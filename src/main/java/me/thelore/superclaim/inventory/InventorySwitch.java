package me.thelore.superclaim.inventory;

import me.thelore.superclaim.inventory.impl.MainInventory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InventorySwitch {
    private List<Switchable> inventoryList;

    public InventorySwitch() {
        this.inventoryList = new ArrayList<>();

        inventoryList.add(new MainInventory());
    }

    public void switchInventory(Player player, InventoryType inventoryType) {
        Switchable switchable = getFromType(inventoryType);

        if(switchable == null) {
            return;
        }

        switchable.open(player);
    }

    private Switchable getFromType(InventoryType inventoryType) {
        return inventoryList.stream().filter(i -> i.getInventory() == inventoryType).findAny().orElse(null);
    }
}
