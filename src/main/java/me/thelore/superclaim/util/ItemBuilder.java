package me.thelore.superclaim.util;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    public static ItemStack build(Material material, String title, String... lore) {
        ItemStack temp = new ItemStack(material);
        ItemMeta tempMeta = temp.getItemMeta();
        tempMeta.setDisplayName(title);
        tempMeta.setLore(Arrays.asList(lore));
        temp.setItemMeta(tempMeta);

        return temp;
    }

}
