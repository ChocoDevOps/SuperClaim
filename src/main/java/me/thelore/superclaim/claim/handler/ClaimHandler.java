package me.thelore.superclaim.claim.handler;

import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.storage.impl.ClaimStorage;

import java.lang.management.ClassLoadingMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClaimHandler {
    private final ClaimStorage claimStorage;
    private List<Claim> claimList;

    public ClaimHandler() {
        this.claimStorage = new ClaimStorage();
        this.claimList = new ArrayList<>();
    }

    public boolean createClaim(Claim claim) {
        if(getClaim(claim.getClaimIdentifier().getId()) != null) {
            return false;
        }

        claimList.add(claim);
        return true;
    }

    public boolean removeClaim(Claim claim) {
        if(getClaim(claim.getClaimIdentifier().getId()) == null) {
            return false;
        }

        claimList.remove(claim);
        return true;
    }

    public Claim getClaim(String claimId) {
        return claimList.stream().filter(c -> c.getClaimIdentifier().getId().equals(claimId)).findAny().orElse(null);
    }

    public void saveData() {
        claimList.forEach(claimStorage::saveClaim);
    }

    public void loadData() {
        this.claimList = claimStorage.fetchClaims();
    }
}
