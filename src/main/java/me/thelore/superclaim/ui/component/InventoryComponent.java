package me.thelore.superclaim.ui.component;

import me.thelore.superclaim.ui.InventoryMenu;
import me.thelore.superclaim.ui.Menu;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryComponent {
    private Menu menu;
    private int slot;

    public InventoryComponent(Menu menu, int slot) {
        this.menu = menu;
        this.slot = slot;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getSlot() {
        return slot;
    }

    public abstract ItemStack getItemStack();
}
