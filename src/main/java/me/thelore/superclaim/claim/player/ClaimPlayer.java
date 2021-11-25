package me.thelore.superclaim.claim.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.thelore.superclaim.claim.permission.ClaimPermission;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ClaimPlayer {
    private String name;
    private List<ClaimPermission> claimPermissions;

    public void removePermission(ClaimPermission claimPermission) {
        claimPermissions.remove(claimPermission);
    }

    public void addPermission(ClaimPermission claimPermission) {
        if(!claimPermissions.contains(claimPermission)) {
            claimPermissions.add(claimPermission);
        }
    }

    public boolean hasPermission(ClaimPermission claimPermission) {
        return claimPermissions.contains(claimPermission);
    }

}
