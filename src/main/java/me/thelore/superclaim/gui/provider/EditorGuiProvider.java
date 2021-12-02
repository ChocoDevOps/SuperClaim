package me.thelore.superclaim.gui.provider;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.chat.Placeholder;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.configuration.Settings;
import me.thelore.superclaim.inventory.ClickableItem;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.inventory.content.InventoryContents;
import me.thelore.superclaim.inventory.content.InventoryProvider;
import me.thelore.superclaim.inventory.content.SlotPos;
import me.thelore.superclaim.util.ItemHelper;
import me.thelore.superclaim.util.selector.AreaSelector;
import me.thelore.superclaim.util.selector.AreaSelectorCallback;
import me.thelore.superclaim.util.selector.NameSelector;
import me.thelore.superclaim.util.selector.NameSelectorCallback;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EditorGuiProvider implements InventoryProvider, Messaging {
    private final SmartInventory gui;
    private final Claim claim;
    private final ClaimHandler claimHandler;
    private final Settings settings;

    public EditorGuiProvider(Player player, Claim claim) {
        this.claim = claim;
        this.claimHandler = SuperClaim.getInstance().getClaimHandler();
        this.settings = SuperClaim.getInstance().getSettings();

        gui = SmartInventory.builder()
                .provider(this)
                .id("editorGui")
                .title(getChatManager().getMessage("claim-editor-title", new Placeholder("{claimName}", claim.getClaimIdentifier().getDisplayName())))
                .closeable(true)
                .size(3, 9)
                .build();

        open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(ItemHelper.build(Material.GRAY_STAINED_GLASS_PANE, " ")));

        contents.set(new SlotPos(1, 1), ClickableItem.of(ItemHelper.build(Material.PAPER, "§aRename claim"), e -> {
            player.closeInventory();

            new NameSelector().record(player, new NameSelectorCallback() {
                @Override
                public void onDone(String newName) {
                    claim.getClaimIdentifier().setDisplayName(newName);
                    getChatManager().sendMessage(player, "name-changed", new Placeholder("{claimName}", newName));
                }
            });
        }));

        int remainingBlocks = ((int) settings.getValue("max-claim-blocks") - claim.getTerritory().getBlocks());

        contents.set(new SlotPos(1, 3), ClickableItem.of(ItemHelper.build(Material.GOLDEN_SHOVEL, getChatManager().getMessage("redefine-claim-title"),
                getChatManager().getMessage("remaining-blocks", new Placeholder("{remainingBlocks}", remainingBlocks + ""))), e -> {
            player.sendMessage(getChatManager().getMessage("select-new-area"));
            player.closeInventory();
            AreaSelector areaSelector = new AreaSelector();
            areaSelector.record(player, new AreaSelectorCallback() {
                @Override
                public void onDone(Territory territory) {
                    if(territory.getBlocks() > (int) settings.getValue("max-claim-blocks")) {
                        getChatManager().sendMessage(player, "too-many-blocks");
                        return;
                    }

                    claim.setTerritory(territory);
                    getChatManager().sendMessage(player, "claim-redefined");
                }

                @Override
                public void onError() {
                    getChatManager().sendMessage(player, "claim-error");
                }
            }, claim.getTerritory());
        }));

        contents.set(new SlotPos(1, 5), ClickableItem.of(ItemHelper.build(Material.PLAYER_HEAD, "§aPlayers permissions"), e -> {
            new PlayerSelectorGuiProvider(player, claim);
        }));

        contents.set(new SlotPos(1, 7), ClickableItem.of(ItemHelper.build(Material.BARRIER, "§4Delete claim"), e -> {
            player.closeInventory();
            SmartInventory gui = SmartInventory.builder()
                    .provider(new ConfirmGuiProvider(new ConfirmCallback() {
                        @Override
                        public void onChoose(boolean confirmed) {
                            if(confirmed) {
                                claimHandler.removeClaim(claim);
                                getChatManager().sendMessage(player, "remove-success");
                            } else {
                                getChatManager().sendMessage(player, "remove-aborted");
                            }
                        }
                    }))
                    .id("confirmGui")
                    .title(getChatManager().getMessage("confirm-title"))
                    .closeable(true)
                    .size(1, 9)
                    .build();
            gui.open(player);
        }));
    }

    private void open(Player player) {
        gui.open(player);
    }
}
