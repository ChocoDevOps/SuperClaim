package me.thelore.superclaim.claim.handler;

import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.storage.impl.ClaimStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.management.ClassLoadingMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClaimHandler {
    private final ClaimStorage claimStorage;
    private List<Claim> claimList;

    public ClaimHandler() {
        this.claimStorage = new ClaimStorage();
        this.claimList = new ArrayList<>();
    }

    public void createClaim(Claim claim) {
        claimList.add(claim);
    }

    public Claim getClaim(Location location) {
        return claimList.parallelStream().filter(c -> c.getTerritory().comprehend(location)).findAny().orElse(null);
    }

    public Claim getClaim(String claimId) {
        return claimList.stream().filter(c -> c.getClaimIdentifier().getId().equals(claimId)).findAny().orElse(null);
    }

    public boolean removeClaim(Claim claim) {
        if(getClaim(claim.getClaimIdentifier().getId()) == null) {
            return false;
        }

        claimList.remove(claim);
        return true;
    }

    public int getClaims(String name) {
        return (int) claimList.stream().filter(c -> c.getClaimIdentifier().getPlayerName().equals(name)).count();
    }

    public void saveData() {
        claimList.forEach(claimStorage::saveClaim);
    }

    public void loadData() {
        this.claimList = claimStorage.fetchClaims();
    }
}
