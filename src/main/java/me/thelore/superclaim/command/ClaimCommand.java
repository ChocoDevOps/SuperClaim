package me.thelore.superclaim.command;

import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.gui.provider.MainGuiProvider;
import me.thelore.superclaim.inventory.SmartInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {
    private SmartInventory gui;
    public ClaimCommand() {
        initGui();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        gui.open(player);

        return true;
    }

    private void initGui() {
        gui = SmartInventory.builder()
                .provider(new MainGuiProvider())
                .id("mainGui")
                .title("Claim")
                .closeable(true)
                .size(3, 9)
                .build();
    }

}
