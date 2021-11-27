package me.thelore.superclaim.command;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.chat.Messaging;
import me.thelore.superclaim.chat.Placeholder;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import me.thelore.superclaim.gui.provider.MainGuiProvider;
import me.thelore.superclaim.gui.provider.MapGuiProvider;
import me.thelore.superclaim.inventory.SmartInventory;
import me.thelore.superclaim.task.AsyncTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClaimCommand implements CommandExecutor, Messaging {
    private final ClaimHandler claimHandler;
    private SmartInventory gui;
    public ClaimCommand() {
        this.claimHandler = SuperClaim.getInstance().getClaimHandler();
        initGui();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            gui.open(player);
            return true;
        }

        if(args.length != 3) {
            player.sendMessage("Usage: /claim <add/remove> [Player] [Claim name]");
        }

        if(args.length == 3) {
            String arg = args[0];
            String target = args[1];
            String claim = args[2];

            if(Bukkit.getPlayer(target) == null) {
                getChatManager().sendMessage(sender, "target-offline");
                return true;
            }

            Claim targetClaim = claimHandler.getClaim(player, claim);
            if(targetClaim == null) {
                getChatManager().sendMessage(sender, "no-claims");
                return true;
            }

            List<ClaimPermission> defaultPermissions = new ArrayList<>();
            defaultPermissions.add(ClaimPermission.VEHICLES);
            defaultPermissions.add(ClaimPermission.CHEST_ACCESS);
            defaultPermissions.add(ClaimPermission.REDSTONE_USE);
            defaultPermissions.add(ClaimPermission.DOOR_USE);
            defaultPermissions.add(ClaimPermission.CAN_BREAK);

            ClaimPlayer claimPlayer = targetClaim.getClaimPlayer(target);
            switch (arg.toLowerCase()) {
                case "add":
                    if(claimPlayer != null) {
                        getChatManager().sendMessage(player, "already-in-team");
                        return true;
                    }
                    ClaimPlayer toAdd = new ClaimPlayer(target, defaultPermissions);
                    targetClaim.addPlayer(toAdd);
                    getChatManager().sendMessage(player, "player-added",
                            new Placeholder("{targetPlayer}", target),
                            new Placeholder("{targetClaim}", targetClaim.getClaimIdentifier().getDisplayName()));
                    break;
                case "remove":
                    if(claimPlayer == null) {
                        getChatManager().sendMessage(player, "no-player-in-team");
                        return true;
                    }
                    targetClaim.removePlayer(claimPlayer);
                    getChatManager().sendMessage(player, "player-removed",
                            new Placeholder("{targetPlayer}", target),
                            new Placeholder("{targetClaim}", targetClaim.getClaimIdentifier().getDisplayName()));
                    break;
                default:
                    break;
            }
        }

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
