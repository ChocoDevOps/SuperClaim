package me.thelore.superclaim.ui.component;

import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.listener.ButtonListener;
import org.bukkit.Material;
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
        this.title = title;
        this.description = new ArrayList<>();

        this.description.addAll(List.of(description));
    }

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
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
