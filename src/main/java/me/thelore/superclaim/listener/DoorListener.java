package me.thelore.superclaim.listener;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoorListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) {
            return;
        }

        if(!event.getClickedBlock().getType().name().toLowerCase().contains("door")) {
            return;
        }

        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location location = event.getClickedBlock().getLocation();

        Claim claim = claimHandler.getClaim(location);

        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.DOOR_USE)) {
            event.setCancelled(true);
        }
    }

}
