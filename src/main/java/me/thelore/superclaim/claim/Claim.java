package me.thelore.superclaim.claim;

import lombok.Getter;
import lombok.Setter;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Claim {
    @Getter
    private final ClaimIdentifier claimIdentifier;
    @Getter
    @Setter
    private Territory territory;
    private List<ClaimPlayer> claimPlayerList;

    public Claim(ClaimIdentifier claimIdentifier, Territory territory) {
        this.claimIdentifier = claimIdentifier;
        this.territory = territory;
        this.claimPlayerList = new ArrayList<>();

        this.claimPlayerList.add(new ClaimPlayer(claimIdentifier.getPlayerName(), new ArrayList<>()));
        claimPlayerList.get(0).getClaimPermissions().addAll(List.of(ClaimPermission.values()));
    }

    public void addPlayer(ClaimPlayer claimPlayer) {
        if (getClaimPlayer(claimPlayer.getName()) != null) {
            return;
        }

        claimPlayerList.add(claimPlayer);
    }

    public void removePlayer(ClaimPlayer claimPlayer) {
        if (getClaimPlayer(claimPlayer.getName()) == null) {
            return;
        }

        claimPlayerList.remove(claimPlayer);
    }

    public ClaimPlayer getClaimPlayer(String name) {
        return claimPlayerList.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);
    }

    public List<String> getPlayersWithPermission(ClaimPermission claimPermission) {
        return claimPlayerList.stream().filter(p -> p.getClaimPermissions().contains(claimPermission)).map(ClaimPlayer::getName).collect(Collectors.toList());
    }

    public List<ClaimPlayer> getPlayers() {
        return claimPlayerList;
    }
}
