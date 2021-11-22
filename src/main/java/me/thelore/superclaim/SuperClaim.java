package me.thelore.superclaim;

import lombok.Getter;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.task.SaveTask;
import me.thelore.superclaim.ui.handler.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperClaim extends JavaPlugin {

    @Getter
    private static SuperClaim instance;

    @Getter
    private ClaimHandler claimHandler;
    @Getter
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        instance = this;

        claimHandler = new ClaimHandler();
        menuHandler = new MenuHandler();

        claimHandler.loadData();
        loadTasks();
    }

    public void onDisable() {
        claimHandler.saveData();
    }

    private void loadTasks() {
        new SaveTask().runTaskTimer(this, 20 * 60 * 5, 20 * 60 * 5);
    }
}
