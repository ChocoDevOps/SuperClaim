package me.thelore.superclaim.listener;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlaceBreakListener implements Listener {

    @EventHandler
    public void on(BlockBreakEvent event) {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location location = event.getBlock().getLocation();

        Claim claim = claimHandler.getClaim(location);
        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.CAN_BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location location = event.getBlock().getLocation();

        Claim claim = claimHandler.getClaim(location);
        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.CAN_BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(BlockIgniteEvent event) {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location blockLocation = event.getBlock().getLocation();

        Claim claim = claimHandler.getClaim(blockLocation);
        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.CAN_BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(PlayerBucketEmptyEvent event) {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();
        Location location = event.getBlockClicked().getLocation();

        Claim claim = claimHandler.getClaim(location);
        if(claim == null) {
            return;
        }

        ClaimPlayer claimPlayer = claim.getClaimPlayer(event.getPlayer().getName());
        if(claimPlayer == null) {
            event.setCancelled(true);
            return;
        }

        if(!claimPlayer.hasPermission(ClaimPermission.CAN_BREAK)) {
            event.setCancelled(true);
        }
    }

}
