package me.thelore.superclaim.inventory;

import lombok.Getter;

public enum InventoryType {
    MAIN_MENU(1),
    PLAYER_SETTINGS(2),
    PLAYERS_LIST(3),
    PLAYERS_SETTINGS(4);

    @Getter
    private final int id;
    InventoryType(int id) {
        this.id = id;
    }
}
