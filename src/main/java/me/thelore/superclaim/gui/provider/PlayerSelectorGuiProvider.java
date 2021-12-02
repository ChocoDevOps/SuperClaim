package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.inventory.content.Pagination;
import me.thelore.superclaim.task.AsyncTask;
import me.thelore.superclaim.util.GuiUtils;
import me.thelore.superclaim.util.ItemHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSelectorGuiProvider implements InventoryProvider, Messaging {
    private final SmartInventory gui;
    private final Claim claim;

    public PlayerSelectorGuiProvider(Player player, Claim claim) {
        this.claim = claim;

        gui = SmartInventory.builder()
                .provider(this)
                .id("playerSelectorGui")
                .title(getChatManager().getMessage("player-selector-title"))
                .closeable(true)
                .size(3, 9)
                .build();

        open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(ItemHelper.build(Material.GRAY_STAINED_GLASS_PANE, " ")));

        Pagination pagination = contents.pagination();

        int listSize = claim.getPlayers().size();
        ClickableItem[] items = new ClickableItem[listSize];

        new AsyncTask(() -> {
            for (int i = 0; i < listSize; i++) {
                ClaimPlayer claimPlayer = claim.getPlayers().get(i);

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(claimPlayer.getName()));
                skullMeta.setDisplayName("§a" + claimPlayer.getName());
                skull.setItemMeta(skullMeta);

                items[i] = ClickableItem.of(skull, e -> {
                    new PermissionGuiProvider(player, claim, claimPlayer);
                });

            }

            GuiUtils.definePagination(player, contents, pagination, items, gui);
        });
    }

    private void open(Player player) {
        gui.open(player);
    }
}
