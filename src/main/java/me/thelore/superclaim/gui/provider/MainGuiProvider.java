package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.configuration.Settings;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.inventory.content.SlotPos;
import me.thelore.superclaim.util.ItemBuilder;
import me.thelore.superclaim.util.selector.AreaSelector;
import me.thelore.superclaim.util.selector.AreaSelectorCallback;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainGuiProvider implements InventoryProvider, Messaging {

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderItem = ItemBuilder.build(Material.GRAY_STAINED_GLASS_PANE, " ");
        contents.fillBorders(ClickableItem.empty(borderItem));

        //New claim Item
        ItemStack newClaim = ItemBuilder.build(Material.EMERALD, getChatManager().getMessage("new-claim-title"), getChatManager().getMessage("new-claim-lore"));
        contents.set(SlotPos.of(1, 2), ClickableItem.of(newClaim, event -> {
            if (!event.getClick().isLeftClick()) {
                return;
            }

            ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
            Settings settings = SuperClaim.getInstance().getSettings();
            int claimId = claimHandler.getClaims(player.getName()) + 1;
            getChatManager().sendMessage(player, "choose-claim-borders");

            new AreaSelector().record(player, new AreaSelectorCallback() {
                @Override
                public void onDone(Territory territory) {
                    if(territory.getBlocks() > (int) settings.getValue("max-claim-blocks")) {
                        getChatManager().sendMessage(player, "too-many-blocks");
                        return;
                    }

                    if(claimHandler.getClaims(player.getName()) > (int) settings.getValue("max-claims-per-player")) {
                        getChatManager().sendMessage(player, "too-many-claims");
                        return;
                    }

                    Claim claim = new Claim(new ClaimIdentifier(player.getName(), claimId), territory);
                    claimHandler.createClaim(claim);
                    getChatManager().sendMessage(player, "claim-created");
                }

                @Override
                public void onError() {
                    getChatManager().sendMessage(player, "claim-error");
                }
            });
        }));

        //Edit claim Item
        ItemStack editClaim = ItemBuilder.build(Material.BLAZE_ROD, getChatManager().getMessage("edit-claim-title"), getChatManager().getMessage("edit-claim-lore"));
        contents.set(new SlotPos(1, 6), ClickableItem.of(editClaim, event -> {
            if (!event.getClick().isLeftClick()) {
                return;
            }

            new SelectorGuiProvider(player);
        }));
    }
}
