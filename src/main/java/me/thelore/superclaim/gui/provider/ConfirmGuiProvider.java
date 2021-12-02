package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.inventory.content.SlotPos;
import me.thelore.superclaim.util.ItemHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ConfirmGuiProvider implements InventoryProvider {
    private ConfirmCallback confirmCallback;

    public ConfirmGuiProvider(ConfirmCallback confirmCallback) {
        this.confirmCallback = confirmCallback;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(new SlotPos(0, 2), ClickableItem.of(ItemHelper.build(Material.GREEN_WOOL, "§a§lCONFIRM"), e -> {
            confirmCallback.onChoose(true);
            player.closeInventory();
        }));

        contents.set(new SlotPos(0, 5), ClickableItem.of(ItemHelper.build(Material.RED_WOOL, "§a§lREFUSE"), e -> {
            confirmCallback.onChoose(false);
            player.closeInventory();
        }));
    }
}
