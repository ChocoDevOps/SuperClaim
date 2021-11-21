package me.thelore.superclaim.claim;

import lombok.Getter;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Claim {
    @Getter
    private final ClaimIdentifier claimIdentifier;
    @Getter
    private final Territory territory;
    private List<ClaimPlayer> claimPlayerList;

    public Claim(ClaimIdentifier claimIdentifier, Territory territory) {
        this.claimIdentifier = claimIdentifier;
        this.territory = territory;
        this.claimPlayerList = new ArrayList<>();

        this.claimPlayerList.add(new ClaimPlayer(claimIdentifier.getPlayerName(), new ArrayList<>()));
        claimPlayerList.get(0).getClaimPermissions().addAll(List.of(ClaimPermission.values()));
    }

    public void addPlayer(ClaimPlayer claimPlayer) {
        if(getClaimPlayer(claimPlayer.getName()) != null) {
            return;
        }

        claimPlayerList.add(claimPlayer);
    }

    public ClaimPlayer getClaimPlayer(String name) {
        List<ClaimPlayer> temp = claimPlayerList.stream().filter(c -> c.getName().equals(name))
                .collect(Collectors.toList());

        if(temp.size() != 0) {
            return temp.get(0);
        }

        return null;
    }

    public List<String> getPlayersWithPermission(ClaimPermission claimPermission) {
        return claimPlayerList.stream().filter(p -> p.getClaimPermissions().contains(claimPermission)).map(ClaimPlayer::getName).collect(Collectors.toList());
    }
}
