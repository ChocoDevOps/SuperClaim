package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.configuration.Settings;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.*;
import me.thelore.superclaim.util.GuiUtils;
import me.thelore.superclaim.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public class SelectorGuiProvider implements InventoryProvider, Messaging {
    private final SmartInventory gui;
    private final ClaimHandler claimHandler;
    private final Settings settings;

    public SelectorGuiProvider(Player player) {
        this.claimHandler = SuperClaim.getInstance().getClaimHandler();
        this.settings = SuperClaim.getInstance().getSettings();

        gui = SmartInventory.builder()
                .provider(this)
                .id("selectorGui")
                .title(getChatManager().getMessage("select-claim-title"))
                .closeable(true)
                .size(3, 9)
                .build();

        open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack borderItem = ItemBuilder.build(Material.GRAY_STAINED_GLASS_PANE, " ");
        contents.fillBorders(ClickableItem.empty(borderItem));

        Pagination pagination = contents.pagination();

        int owned = claimHandler.getClaims(player.getName());
        int maxClaims = (int) settings.getValue("max-claims-per-player");

        ClickableItem[] items = new ClickableItem[7];

        for(int i = 0; i < items.length; i++) {
            AtomicReference<Claim> claimReference = new AtomicReference<>(null);
            if(i < owned) {
                claimReference.set(claimHandler.getClaimList(player.getName()).get(i));
                items[i] = ClickableItem.of(ItemBuilder.build(Material.FILLED_MAP, claimReference.get().getClaimIdentifier().getDisplayName()), event -> {
                    Claim claim = claimReference.get();

                    new EditorGuiProvider(player, claim);
                });
            } else {
                if(i < maxClaims) {
                    items[i] = ClickableItem.empty(ItemBuilder.build(Material.PAPER, getChatManager().getMessage("empty-slot")));
                } else {
                    items[i] = ClickableItem.empty(ItemBuilder.build(Material.BARRIER, getChatManager().getMessage("locked-slot")));
                }
            }
        }

        GuiUtils.definePagination(player, contents, pagination, items, gui);
    }

    private void open(Player player) {
        gui.open(player);
    }
}
