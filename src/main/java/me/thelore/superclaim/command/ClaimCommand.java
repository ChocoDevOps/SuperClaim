package me.thelore.superclaim.command;

import com.sun.tools.javac.Main;
import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.gui.MainGuiProvider;
import me.thelore.superclaim.inventory.InventoryManager;
import me.thelore.superclaim.inventory.SmartInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {
    private final InventoryManager inventoryManager;
    public ClaimCommand() {
        inventoryManager = SuperClaim.getInstance().getInventoryManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        SmartInventory gui = SmartInventory.builder()
                .provider(new MainGuiProvider())
                .id("mainGui")
                .title("Claim")
                .closeable(true)
                .size(3, 9)
                .build();

        gui.open(player);

        return true;
    }

}
