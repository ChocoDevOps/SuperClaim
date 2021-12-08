package me.thelore.superclaim.claim.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ClaimPermission {
    CAN_BREAK, CHEST_ACCESS, REDSTONE_USE, DOOR_USE, VEHICLES, EDIT_CLAIM;

    public static List<ClaimPermission> defaultPermissions() {
        List<ClaimPermission> defaultList = new ArrayList<>();
        defaultList.add(CAN_BREAK);
        defaultList.add(CHEST_ACCESS);
        defaultList.add(REDSTONE_USE);
        defaultList.add(DOOR_USE);
        defaultList.add(VEHICLES);

        return defaultList;
    }
}
