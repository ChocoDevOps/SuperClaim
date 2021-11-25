package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.chat.Placeholder;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PermissionGuiProvider implements InventoryProvider, Messaging {
    private final SmartInventory gui;
    private final Claim claim;
    private final ClaimPlayer claimPlayer;

    public PermissionGuiProvider(Player player, Claim claim,  ClaimPlayer claimPlayer) {
        this.claim = claim;
        this.claimPlayer = claimPlayer;

        gui = SmartInventory.builder()
                .provider(this)
                .id("permissionsGui")
                .title(getChatManager().getMessage("permissions-title", new Placeholder("{playerName}", claimPlayer.getName())))
                .closeable(true)
                .size(3, 9)
                .build();

        open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(ItemBuilder.build(Material.GRAY_STAINED_GLASS_PANE, " ")));

        for(ClaimPermission claimPermission : ClaimPermission.values()) {
            ItemStack item = ItemBuilder.build(claimPlayer.hasPermission(claimPermission) ? Material.GREEN_BANNER : Material.RED_BANNER, ChatColor.GREEN + claimPermission.name().toUpperCase().replace("_", " "));
            contents.add(ClickableItem.of(item, e -> {
                if(claim.getClaimIdentifier().getPlayerName().equals(claimPlayer.getName())) {
                    getChatManager().sendMessage(player, "cant-edit-permissions");
                    return;
                }

                if(claimPlayer.hasPermission(claimPermission)) {
                    claimPlayer.removePermission(claimPermission);
                } else {
                    claimPlayer.addPermission(claimPermission);
                }
                gui.open(player);
            }));
        }
    }

    private void open(Player player) {
        gui.open(player);
    }
}
