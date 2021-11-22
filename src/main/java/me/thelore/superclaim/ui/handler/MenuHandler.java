package me.thelore.superclaim.ui.handler;

import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.ui.InventoryMenu;
import me.thelore.superclaim.ui.Menu;
import me.thelore.superclaim.ui.component.InventoryComponent;
import me.thelore.superclaim.ui.component.ItemButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class MenuHandler implements Listener {
    private final List<Menu> menuList;
    private final Map<UUID, Menu> openedMenuList;

    public MenuHandler() {
        this.menuList = new ArrayList<>();
        this.openedMenuList = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, SuperClaim.getInstance());
    }

    public void registerMenu(Menu menu) {
        this.menuList.add(menu);
    }

    public void openMenu(Player player, Menu menu) {
        String title = menu.getTitle();
        int size = calculateSize(menu);

        Inventory inventory = Bukkit.createInventory(null, size == 0 ? 9 : size, title);

        for(InventoryComponent components : menu.getComponents()) {
            inventory.setItem(components.getSlot(), components.getItemStack());
        }

        player.openInventory(inventory);
        openedMenuList.put(player.getUniqueId(), menu);
    }

    private int calculateSize(Menu menu) {
        int biggerNumber = menu.getComponents().stream().mapToInt(InventoryComponent::getSlot).filter(c -> c >= 0).max().orElse(0);

        return ((biggerNumber / 9) + (biggerNumber % 9 > 0 ? 1 : 0)) * 9;
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        UUID playerUuid = event.getWhoClicked().getUniqueId();
        if(!openedMenuList.containsKey(playerUuid)) {
            return;
        }

        Menu menu = openedMenuList.get(playerUuid);
        int clickedSlot = event.getSlot();

        InventoryComponent component = menu.getComponent(clickedSlot);
        if(component != null) {
            event.setCancelled(true);

            if(component instanceof ItemButton) {
                ((ItemButton) component).propagateClick();
            }
        }
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        this.openedMenuList.remove(event.getPlayer().getUniqueId());
    }
}
