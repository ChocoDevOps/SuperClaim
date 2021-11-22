package me.thelore.superclaim.command;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.inventory.InventoryType;
import me.thelore.superclaim.ui.InventoryMenu;
import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.component.DecorationItem;
import me.thelore.superclaim.ui.component.ItemButton;
import me.thelore.superclaim.ui.handler.MenuHandler;
import me.thelore.superclaim.ui.listener.ButtonListener;
import me.thelore.superclaim.utill.AreaSelector;
import me.thelore.superclaim.utill.SelectorCallback;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.geom.Area;
import java.util.Objects;

public class ClaimCommand implements CommandExecutor {
    private final MenuHandler menuHandler;
    private final ClaimHandler claimHandler;
    private final AreaSelector areaSelector;

    public ClaimCommand() {
        menuHandler = SuperClaim.getInstance().getMenuHandler();
        claimHandler = SuperClaim.getInstance().getClaimHandler();
        areaSelector = SuperClaim.getInstance().getAreaSelector();

        registerUi();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        menuHandler.openMenu(player, InventoryType.MAIN_MENU.getId());

        registerListeners();

        return true;
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
    }

    private void registerListeners() {
        ItemButton createClaim = (ItemButton) menuHandler.getMenuById(InventoryType.MAIN_MENU.getId()).getComponent(10);
        createClaim.setButtonListener(new ButtonListener() {
            @Override
            public void onClick(Player whoClicked) {
                int claimId = claimHandler.getClaims(whoClicked.getName()) + 1;

                whoClicked.sendMessage("Per favore, seleziona i due estremi del tuo claim");
                Objects.requireNonNull(whoClicked.getPlayer(), "Player must be not null");

                areaSelector.onDone(whoClicked.getPlayer(), new SelectorCallback() {
                    @Override
                    public void onDone(Territory territory) {
                        Claim claim = new Claim(new ClaimIdentifier(whoClicked.getName(), claimId), territory);
                        claimHandler.createClaim(claim);
                        whoClicked.sendMessage("Territorio selezionato correttamente, claim creato");
                    }
                });
            }
        });
    }
}
