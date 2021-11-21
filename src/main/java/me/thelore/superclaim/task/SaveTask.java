package me.thelore.superclaim.task;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
    @Override
    public void run() {
        ClaimHandler claimHandler = SuperClaim.getInstance().getClaimHandler();

        claimHandler.saveData();
    }
}
