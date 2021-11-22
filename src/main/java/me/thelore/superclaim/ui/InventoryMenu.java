package me.thelore.superclaim.ui;

import me.thelore.superclaim.ui.component.InventoryComponent;

import java.util.ArrayList;
import java.util.List;

public class InventoryMenu implements Menu {
    private final int id;

    private int size;

    private final String title;
    private final List<InventoryComponent> componentList;

    public InventoryMenu(int id, String title) {
        this.id = id;

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

        componentList.add(component);
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public List<Integer> getFreeSlots() {
        List<Integer> toReturn = new ArrayList<>();
        for(int i = 0; i < getSize(); i++) {
            if(getComponent(i) == null) {
                toReturn.add(i);
            }
        }
        return toReturn;
    }

}
