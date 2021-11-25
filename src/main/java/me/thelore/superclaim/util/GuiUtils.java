package me.thelore.superclaim.util;

import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.Pagination;
import me.thelore.superclaim.inventory.content.SlotIterator;
import me.thelore.superclaim.inventory.content.SlotPos;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuiUtils {
    public static void definePagination(Player player, InventoryContents contents, Pagination pagination, ClickableItem[] items, SmartInventory gui) {
        pagination.setItems(items);
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, new SlotPos(1, 1)));

        contents.set(2, 3, ClickableItem.of(ItemBuilder.build(Material.ARROW, "Previous page"),
                e -> gui.open(player, pagination.previous().getPage())));
        contents.set(2, 5, ClickableItem.of(ItemBuilder.build(Material.ARROW, "Next page"),
                e -> gui.open(player, pagination.next().getPage())));
    }
}
