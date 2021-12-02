package me.thelore.superclaim.claim.handler;

import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.storage.impl.ClaimStorage;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

    public void createClaim(Claim claim) {
        claimList.add(claim);
    }

    public Claim getClaim(Location location) {
        return claimList.stream().filter(c -> c.getTerritory().comprehend(location)).findAny().orElse(null);
    }

    public List<Claim> getClaim(Chunk chunk) {
        List<Claim> temp = new ArrayList<>();

        for(Claim claim : claimList) {
            for(int x = claim.getTerritory().getMinX(); x < claim.getTerritory().getMaxX(); x++) {
                for(int z = claim.getTerritory().getMinZ(); z < claim.getTerritory().getMaxZ(); z++) {
                    Location loc = new Location(chunk.getWorld(), x, 1, z);
                    if(!(loc.getChunk().getX() == chunk.getX() && loc.getChunk().getZ() == chunk.getZ())) {
                        continue;
                    }

                    if(!temp.contains(claim)) {
                        temp.add(claim);
                    }
                }
            }
        }
        return temp;
    }

    public Claim getClaim(String claimId) {
        return claimList.stream().filter(c -> c.getClaimIdentifier().getId().equals(claimId)).findAny().orElse(null);
    }

    public Claim getClaim(Player player, String name) {
        return claimList.stream().filter(c -> c.getClaimIdentifier().getPlayerName().equals(player.getName()) && c.getClaimIdentifier().getDisplayName().equals(name)).findAny().orElse(null);
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

    public List<Claim> getClaimList(String name) {
        return claimList.stream().filter(c -> c.getClaimIdentifier().getPlayerName().equals(name)).collect(Collectors.toList());
    }

    public void saveData() {
        boolean toReset = true;
        for(Claim claim : claimList) {
            claimStorage.saveClaim(claim, toReset);
            toReset = false;
        }
    }

    public void loadData() {
        this.claimList = claimStorage.fetchClaims();
    }
}
