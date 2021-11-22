package me.thelore.superclaim.ui.component;

import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.listener.ButtonListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemButton extends InventoryComponent {

    private final Material material;
    private final String title;
    private final List<String> description;

    private ButtonListener buttonListener;

    public ItemButton(Menu menu, int slot, Material material, String title, String... description) {
        super(menu, slot);

        this.material = material;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.description = new ArrayList<>();

        for(String d : description) {
            this.description.add(ChatColor.translateAlternateColorCodes('&', d));
        }
    }

    public ItemButton(Menu menu, int slot, ItemStack itemStack) {
        super(menu, slot);

        this.material = itemStack.getType();
        this.title = ChatColor.translateAlternateColorCodes('&', itemStack.getItemMeta().getDisplayName());
        this.description = itemStack.getItemMeta().getLore();

        Objects.requireNonNull(description, "Description is required");
        for(String d : description) {
            this.description.add(ChatColor.translateAlternateColorCodes('&', d));
        }
    }

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void propagateClick(Player whoClicked) {
        if(buttonListener != null) {
            buttonListener.onClick(whoClicked);
        }
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDescription() {
        return description;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(getMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();

        Objects.requireNonNull(itemMeta, "ItemMeta cannot be null");

        itemMeta.setDisplayName(getTitle());
        itemMeta.setLore(getDescription());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
