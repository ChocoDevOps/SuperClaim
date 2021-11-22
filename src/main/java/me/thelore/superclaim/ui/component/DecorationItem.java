package me.thelore.superclaim.ui.component;

import me.thelore.superclaim.ui.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class DecorationItem extends InventoryComponent {
    private final Material material;
    private final String title;

    public DecorationItem(Menu menu, int slot, Material material, String title) {
        super(menu, slot);
        this.material = material;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemMeta, "ItemMeta cannot be null");

        itemMeta.setDisplayName(getTitle());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
