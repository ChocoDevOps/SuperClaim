package me.thelore.superclaim.command;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.inventory.InventorySwitch;
import me.thelore.superclaim.inventory.InventoryType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
