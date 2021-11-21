package me.thelore.superclaim.claim;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ClaimIdentifier {
    private String playerName;
    private int claimCount;
    private String id;

    public ClaimIdentifier(String playerName, int claimCount) {
        this.playerName = playerName;
        this.claimCount = claimCount;
        this.id = playerName + "#" + claimCount;
    }
}
