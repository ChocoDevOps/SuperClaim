package me.thelore.superclaim.gui;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.inventory.content.SlotPos;
import me.thelore.superclaim.util.ItemBuilder;
import me.thelore.superclaim.util.selector.AreaSelector;
import me.thelore.superclaim.util.selector.SelectorCallback;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainGuiProvider implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderItem = ItemBuilder.build(Material.GRAY_STAINED_GLASS_PANE, " ");
        contents.fillBorders(ClickableItem.empty(borderItem));

        ItemStack claim = ItemBuilder.build(Material.EMERALD, "§aNew claim", "§bClick to create a new claim");
        contents.set(SlotPos.of(1, 1), ClickableItem.of(claim, event -> {
            if (!event.getClick().isLeftClick()) {
                return;
            }

            ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
            int claimId = claimHandler.getClaims(player.getName()) + 1;
            player.sendMessage("Per favore, seleziona i due estremi del tuo claim");

            new AreaSelector().record(player, new SelectorCallback() {
                @Override
                public void onDone(Territory territory) {
                    Claim claim = new Claim(new ClaimIdentifier(player.getName(), claimId), territory);
                    claimHandler.createClaim(claim);
                    player.sendMessage("Claim creato con successo!");
                }

                @Override
                public void onError() {
                    player.sendMessage("Qualcosa è andato storto durante la creazione del claim");
                }
            });
        }));
    }
}
