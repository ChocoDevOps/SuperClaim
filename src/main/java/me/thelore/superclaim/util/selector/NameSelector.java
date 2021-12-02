package me.thelore.superclaim.util.selector;

import me.thelore.superclaim.SuperClaim;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NameSelector implements Listener {
    private final List<UUID> selecting;
    private NameSelectorCallback selectorCallback;

    public NameSelector() {
        selecting = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(this, SuperClaim.getInstance());
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        if(!selecting.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);

        String name = event.getMessage().split(" ")[0];
        selecting.remove(event.getPlayer().getUniqueId());
        selectorCallback.onDone(name);
    }

    public void record(Player player, NameSelectorCallback selectorCallback) {
        this.selecting.add(player.getUniqueId());
        this.selectorCallback = selectorCallback;
    }
}
