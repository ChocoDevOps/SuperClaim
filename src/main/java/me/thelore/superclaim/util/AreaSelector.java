package me.thelore.superclaim.util;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AreaSelector implements Listener {
    private final List<UUID> selecting;

    private final ClaimHandler claimHandler;

    private SelectorCallback selectorCallback;

    private Location p1;
    private Location p2;

    public AreaSelector() {
        selecting = new ArrayList<>();

        claimHandler = SuperClaim.getInstance().getClaimHandler();

        Bukkit.getPluginManager().registerEvents(this, SuperClaim.getInstance());
    }

    public void record(Player player, SelectorCallback selectorCallback) {
        this.selecting.add(player.getUniqueId());
        this.selectorCallback = selectorCallback;
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(selecting.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);

            if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                p1 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("Punto 1 selezionato");
            }

            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p2 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("Punto 2 selezionato");
            }

            if(p1 != null && p2 != null) {
                if(claimHandler.getClaim(p1) != null || claimHandler.getClaim(p2) != null) {
                    selectorCallback.onError();
                    return;
                }

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
