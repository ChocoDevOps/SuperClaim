package me.thelore.superclaim.command;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.inventory.InventorySwitch;
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

import java.util.Objects;

public class ClaimCommand implements CommandExecutor {
    private final InventorySwitch inventorySwitch;
    public ClaimCommand() {
        inventorySwitch = SuperClaim.getInstance().getInventorySwitch();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        inventorySwitch.switchInventory(player, InventoryType.MAIN_MENU);

        return true;
    }
}
