package me.thelore.superclaim.inventory.impl;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.inventory.InventoryType;
import me.thelore.superclaim.inventory.Switchable;
import me.thelore.superclaim.ui.InventoryMenu;
import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.component.DecorationItem;
import me.thelore.superclaim.ui.component.ItemButton;
import me.thelore.superclaim.ui.handler.MenuHandler;
import me.thelore.superclaim.ui.listener.ButtonListener;
import me.thelore.superclaim.utill.AreaSelector;
import me.thelore.superclaim.utill.SelectorCallback;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MainInventory implements Switchable {

    private final AreaSelector areaSelector;
    private final MenuHandler menuHandler;
    private final ClaimHandler claimHandler;

    public MainInventory() {
        this.areaSelector = SuperClaim.getInstance().getAreaSelector();
        this.menuHandler = SuperClaim.getInstance().getMenuHandler();
        this.claimHandler = SuperClaim.getInstance().getClaimHandler();

        registerUi();
    }

    @Override
    public void open(Player player) {
        menuHandler.openMenu(player, getInventory().getId());
    }

    private void registerUi() {
        Menu menu = new InventoryMenu(InventoryType.MAIN_MENU.getId(), "Main menu");
        int[] border = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

        for(int i : border) {
            menu.addComponent(new DecorationItem(menu, i, Material.GRAY_STAINED_GLASS_PANE, " "));
            menu.addComponent(new ItemButton(menu, 10, Material.EMERALD, "&aCreate claim", "&bClick here to add a new claim"));
            menu.addComponent(new ItemButton(menu, 12, Material.BLAZE_ROD, "&aEdit claim", "&bClick here to edit a claim"));
            menu.addComponent(new ItemButton(menu, 14, Material.BARRIER, "&aDelete claim", "&bClick here to delete a claim"));
            menu.addComponent(new ItemButton(menu, 16, Material.SUGAR, "&aPlayer List", "&bClick here to view and edit players that have access to claim"));
        }

        menuHandler.registerMenu(menu);
        registerListeners();
    }

    private void registerListeners() {
        ItemButton createClaim = (ItemButton) menuHandler.getMenuById(InventoryType.MAIN_MENU.getId()).getComponent(10);
        createClaim.setButtonListener(new ButtonListener() {
            @Override
            public void onClick(Player whoClicked) {
                int claimId = claimHandler.getClaims(whoClicked.getName()) + 1;
                whoClicked.sendMessage("Per favore, seleziona i due estremi del tuo claim");

                Objects.requireNonNull(whoClicked.getPlayer(), "Player must be not null");
                areaSelector.record(whoClicked.getPlayer(), new SelectorCallback() {
                    @Override
                    public void onDone(Territory territory) {
                        Claim claim = new Claim(new ClaimIdentifier(whoClicked.getName(), claimId), territory);
                        claimHandler.createClaim(claim);
                        whoClicked.sendMessage("Territorio selezionato correttamente, claim creato");
                    }

                    @Override
                    public void onError() {
                        whoClicked.sendMessage("Qualcosa è andato storto durante la creazione del claim");
                    }
                });
            }
        });
    }

    @Override
    public InventoryType getInventory() {
        return InventoryType.MAIN_MENU;
    }
}
