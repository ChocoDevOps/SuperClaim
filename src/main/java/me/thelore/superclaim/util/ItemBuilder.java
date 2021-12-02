package me.thelore.superclaim.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    public static ItemStack build(Material material, String title, String... lore) {
        ItemStack temp = new ItemStack(material);
        ItemMeta tempMeta = temp.getItemMeta();
        tempMeta.setDisplayName(title);
        tempMeta.setLore(Arrays.asList(lore));
        temp.setItemMeta(tempMeta);

        return temp;
    }

    public static ItemStack build(Material material, String title, List<String> lore) {
        ItemStack temp = new ItemStack(material);
        ItemMeta tempMeta = temp.getItemMeta();
        tempMeta.setDisplayName(title);
        tempMeta.setLore(lore);
        temp.setItemMeta(tempMeta);

        return temp;
    }

}
