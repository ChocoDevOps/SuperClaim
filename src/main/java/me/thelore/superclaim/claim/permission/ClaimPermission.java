package me.thelore.superclaim.claim.permission;

import java.util.Arrays;
import java.util.List;

public enum ClaimPermission {
    CAN_BREAK, CHEST_ACCESS, REDSTONE_USE, DOOR_USE, VEHICLES, EDIT_CLAIM;

    public static List<ClaimPermission> defaultPermissions() {
        return Arrays.asList(CAN_BREAK, CHEST_ACCESS, REDSTONE_USE, DOOR_USE, VEHICLES);
    }
}
