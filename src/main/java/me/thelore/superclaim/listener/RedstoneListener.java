package me.thelore.superclaim.listener;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class RedstoneListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location location = event.getPlayer().getLocation();

        if(!event.getAction().equals(Action.PHYSICAL)
        && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        String blockName = event.getClickedBlock().getType().name().toLowerCase();

        Objects.requireNonNull(event.getClickedBlock());
        if(!blockName.contains("button") &&
        !blockName.contains("pressur") &&
        !blockName.contains("target") &&
        !blockName.contains("leve")) {
            return;
        }

        Claim claim = claimHandler.getClaim(location);
        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.REDSTONE_USE)) {
            event.setCancelled(true);
        }
    }

}
