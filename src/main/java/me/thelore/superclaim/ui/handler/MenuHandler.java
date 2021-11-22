package me.thelore.superclaim.ui.handler;

import me.thelore.superclaim.ui.InventoryMenu;
import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.component.InventoryComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {
    private final List<Menu> menuList;

    public MenuHandler() {
        this.menuList = new ArrayList<>();
    }

    public void registerMenu(Menu menu) {
        this.menuList.add(menu);
    }

    public void openMenu(Player player, Menu menu) {
        String title = menu.getTitle();
        int size = calculateSize(menu);

        Inventory inventory = Bukkit.createInventory(null, size, title);

        for(InventoryComponent components : menu.getComponents()) {
            inventory.setItem(components.getSlot(), components.getItemStack());
        }
    }

    private int calculateSize(Menu menu) {
        int biggerNumber = menu.getComponents().stream().mapToInt(InventoryComponent::getSlot).filter(c -> c >= 0).max().orElse(0);

        return ((biggerNumber / 9) + (biggerNumber % 9 > 0 ? 1 : 0)) * 9;
    }
}
