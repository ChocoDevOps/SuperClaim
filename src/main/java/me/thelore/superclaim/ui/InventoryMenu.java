package me.thelore.superclaim.ui;

import me.thelore.superclaim.ui.component.InventoryComponent;

import java.util.ArrayList;
import java.util.List;

public class InventoryMenu implements Menu {
    private final String title;
    private final List<InventoryComponent> componentList;

    public InventoryMenu(String title) {
        this.title = title;
        this.componentList = new ArrayList<>();
    }

    @Override
    public List<InventoryComponent> getComponents() {
        return componentList;
    }

    @Override
    public void addComponent(InventoryComponent component) {
        if(getComponent(component.getSlot()) != null) {
            return;
        }

        addComponent(component);
    }

    @Override
    public void removeComponent(InventoryComponent component) {
        InventoryComponent toRemove = getComponent(component.getSlot());

        if(toRemove == null) {
            return;
        }

        componentList.remove(toRemove);
    }

    @Override
    public InventoryComponent getComponent(int slot) {
        return componentList.stream().filter(c -> c.getSlot() == slot).findAny().orElse(null);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
