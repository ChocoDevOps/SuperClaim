package me.thelore.superclaim.utill;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Territory;
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

    private SelectorCallback selectorCallback;

    private Location p1;
    private Location p2;

    public AreaSelector() {
        selecting = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(this, SuperClaim.getInstance());
    }

    public void onDone(Player player, SelectorCallback selectorCallback) {
        this.selecting.add(player.getUniqueId());
        this.selectorCallback = selectorCallback;
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(selecting.contains(event.getPlayer().getUniqueId())) {
            if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                p1 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("Punto 1 selezionato");
            }

            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p2 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("Punto 2 selezionato");
            }

            if(p1 != null && p2 != null) {
                Territory territory = new Territory(p1.getWorld().getName(), p1.getBlockX(), p1.getBlockY(), p1.getBlockZ(), p2.getBlockX(), p2.getBlockY(), p2.getBlockZ());
                selectorCallback.onDone(territory);
                selecting.remove(event.getPlayer().getUniqueId());
            }
        }

    }
}
