package me.thelore.superclaim.ui;

import me.thelore.superclaim.ui.component.InventoryComponent;

import java.util.List;

public interface Menu {
    List<InventoryComponent> getComponents();
    void addComponent(InventoryComponent component);
    void removeComponent(InventoryComponent component);
    InventoryComponent getComponent(int slot);
    String getTitle();
    int getId();
    int getSize();
    void setSize(int size);
    List<Integer> getFreeSlots();
}
