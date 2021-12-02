package me.thelore.superclaim.claim;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ClaimIdentifier {
    private final String playerName;
    private final int claimCount;
    private final String id;
    @Setter
    private String displayName;

    public ClaimIdentifier(String playerName, int claimCount) {
        this.playerName = playerName;
        this.claimCount = claimCount;
        this.id = playerName + "#" + claimCount;
        this.displayName = id;
    }
}
