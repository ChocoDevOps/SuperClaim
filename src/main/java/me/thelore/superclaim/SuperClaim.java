package me.thelore.superclaim;

import lombok.Getter;
import me.thelore.superclaim.chat.ChatManager;
import me.thelore.superclaim.claim.handler.ClaimHandler;
import me.thelore.superclaim.command.ClaimCommand;
import me.thelore.superclaim.configuration.Settings;
import me.thelore.superclaim.inventory.InventoryManager;
import me.thelore.superclaim.task.SaveTask;
import me.thelore.superclaim.util.selector.AreaSelector;
import me.thelore.superclaim.util.selector.NameSelector;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperClaim extends JavaPlugin {

    @Getter
    private static SuperClaim instance;

    @Getter
    private Settings settings;
    @Getter
    private ChatManager chatManager;

    @Getter
    private ClaimHandler claimHandler;
    @Getter
    private InventoryManager inventoryManager;
    @Getter
    private AreaSelector areaSelector;
    @Getter
    private NameSelector nameSelector;

    @Override
    public void onEnable() {
        instance = this;

        settings = new Settings();
        chatManager = new ChatManager();

        claimHandler = new ClaimHandler();
        inventoryManager = new InventoryManager(this);
        areaSelector = new AreaSelector();
        nameSelector = new NameSelector();

        inventoryManager.init();
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
