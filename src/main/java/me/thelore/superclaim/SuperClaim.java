package me.thelore.superclaim;

import lombok.Getter;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.command.ClaimCommand;
import me.thelore.superclaim.inventory.InventorySwitch;
import me.thelore.superclaim.task.SaveTask;
import me.thelore.superclaim.ui.handler.MenuHandler;
import me.thelore.superclaim.util.AreaSelector;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperClaim extends JavaPlugin {

    @Getter
    private static SuperClaim instance;

    @Getter
    private ClaimHandler claimHandler;
    @Getter
    private MenuHandler menuHandler;
    @Getter
    private AreaSelector areaSelector;

    @Getter
    private InventorySwitch inventorySwitch;

    @Override
    public void onEnable() {
        instance = this;

        claimHandler = new ClaimHandler();
        menuHandler = new MenuHandler();
        areaSelector = new AreaSelector();

        inventorySwitch = new InventorySwitch();

        claimHandler.loadData();
        loadTasks();
        registerCommands();
    }

    public void onDisable() {
        claimHandler.saveData();
    }

    private void loadTasks() {
        new SaveTask().runTaskTimerAsynchronously(this, 20 * 60 * 5, 20 * 60 * 5);
    }

    private void registerCommands() {
        getCommand("claim").setExecutor(new ClaimCommand());
    }
}
