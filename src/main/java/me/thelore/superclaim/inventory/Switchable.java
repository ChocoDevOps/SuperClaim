package me.thelore.superclaim.inventory;

import org.bukkit.entity.Player;

public interface Switchable {
    InventoryType getInventory();
    void open(Player player);
}
