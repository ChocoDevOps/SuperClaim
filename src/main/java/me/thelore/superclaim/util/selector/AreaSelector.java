package me.thelore.superclaim.util.selector;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.animation.BlockOverlay;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AreaSelector implements Listener, Messaging {
    private final List<UUID> selecting;

    private final ClaimHandler claimHandler;

    private AreaSelectorCallback selectorCallback;

    private BlockOverlay blockOverlay;

    private Location p1;
    private Location p2;

    public AreaSelector() {
        selecting = new ArrayList<>();

        claimHandler = SuperClaim.getInstance().getClaimHandler();

        Bukkit.getPluginManager().registerEvents(this, SuperClaim.getInstance());
    }

    public void record(Player player, AreaSelectorCallback selectorCallback) {
        this.selecting.add(player.getUniqueId());
        this.selectorCallback = selectorCallback;

        blockOverlay = new BlockOverlay(player, 0.50);
        blockOverlay.start();
    }

    public void record(Player player, AreaSelectorCallback selectorCallback, Territory territory) {
        this.selecting.add(player.getUniqueId());
        this.selectorCallback = selectorCallback;

        blockOverlay = new BlockOverlay(player, 0.50);
        blockOverlay.start();

        setOldTerritory(territory);
    }

    private void setOldTerritory(Territory territory) {
        Location minLocation = new Location(Bukkit.getWorld(territory.getWorldName()), territory.getMinX(), territory.getMinY(), territory.getMinZ());
        Location maxLocation = new Location(Bukkit.getWorld(territory.getWorldName()), territory.getMaxX(), territory.getMaxY(), territory.getMaxZ());

        blockOverlay.setP1(minLocation);
        blockOverlay.setP2(maxLocation);
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(selecting.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);

            if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                p1 = event.getClickedBlock().getLocation();
                blockOverlay.setP1(p1);
                getChatManager().sendMessage(event.getPlayer(), "point-1-selected");
            }

            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p2 = event.getClickedBlock().getLocation();
                blockOverlay.setP2(p2);
                getChatManager().sendMessage(event.getPlayer(), "point-2-selected");
            }

            if(p1 != null && p2 != null) {
                Player player = event.getPlayer();
                if((claimHandler.getClaim(p1) != null && !claimHandler.getClaim(p1).getClaimPlayer(player.getName()).hasPermission(ClaimPermission.EDIT_CLAIM)) ||
                        (claimHandler.getClaim(p2) != null && !claimHandler.getClaim(p2).getClaimPlayer(player.getName()).hasPermission(ClaimPermission.EDIT_CLAIM))) {
                    selectorCallback.onError();
                    selecting.remove(event.getPlayer().getUniqueId());
                    blockOverlay.stop();
                    return;
                }
                blockOverlay.stop();

                int xMin = Math.min(p1.getBlockX(), p2.getBlockX());
                int yMin = Math.min(p1.getBlockY(), p2.getBlockY());
                int zMin = Math.min(p1.getBlockZ(), p2.getBlockZ());

                int xMax = Math.max(p1.getBlockX(), p2.getBlockX());
                int yMax = Math.max(p1.getBlockY(), p2.getBlockY());
                int zMax = Math.max(p1.getBlockZ(), p2.getBlockZ());

                Territory territory = new Territory(p1.getWorld().getName(), xMin, yMin, zMin, xMax, yMax, zMax);
                selectorCallback.onDone(territory);
                selecting.remove(event.getPlayer().getUniqueId());
            }
        }

    }
}
