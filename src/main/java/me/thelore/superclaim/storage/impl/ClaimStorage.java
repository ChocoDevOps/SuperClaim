package me.thelore.superclaim.storage.impl;

import com.sun.source.tree.UsesTree;
import me.thelore.superclaim.claim.Claim;
import me.thelore.superclaim.claim.ClaimIdentifier;
import me.thelore.superclaim.claim.Territory;
import me.thelore.superclaim.claim.permission.ClaimPermission;
import me.thelore.superclaim.claim.player.ClaimPlayer;
import me.thelore.superclaim.storage.Configuration;
import me.thelore.superclaim.utill.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ClaimStorage extends Configuration {
    public ClaimStorage() {
        super("claims", true);
    }

    public void saveClaim(Claim claim) {
        FileConfiguration fileConfiguration = this.getConfiguration();

        String claimId = claim.getClaimIdentifier().getId();
        String basePath = "claims." + claimId;

        fileConfiguration.set(basePath, null);

        Arrays.stream(ClaimPermission.values()).forEach(c -> {
            fileConfiguration.set(basePath + "." + c.name(), claim.getPlayersWithPermission(c));
        });

        Location minLocation = LocationUtil.getLocations(claim.getTerritory())[0];
        Location maxLocation = LocationUtil.getLocations(claim.getTerritory())[1];

        fileConfiguration.set(basePath + ".meta.area", LocationUtil.serialize(minLocation) + ":" + LocationUtil.serialize(maxLocation));
        saveConfig();
    }

    public List<Claim> fetchClaims() {
        List<Claim> toReturn = new ArrayList<>();

        for (String claimString : getConfiguration().getConfigurationSection("claims").getKeys(false)) {
            String basePath = "claims." + claimString;

            ClaimIdentifier claimIdentifier = new ClaimIdentifier(claimString.split("#")[0], Integer.parseInt(claimString.split("#")[1]));

            String areaPoints = getConfiguration().getString(basePath + ".meta.area");
            String[] point = areaPoints.split(":");

            Location minLocation = LocationUtil.deserialize(point[0]);
            Location maxLocation = LocationUtil.deserialize(point[1]);

            Territory territory = new Territory(minLocation.getWorld().getName(), minLocation.getBlockX(), minLocation.getBlockY(), minLocation.getBlockZ(),
                    maxLocation.getBlockX(), maxLocation.getBlockY(), maxLocation.getBlockZ());

            Map<String, List<ClaimPermission>> claimPermission = new HashMap<>();
            for (String permission : getConfiguration().getConfigurationSection(basePath).getKeys(false)) {
                if (permission.equalsIgnoreCase("meta")) {
                    continue;
                }

                String permissionPath = getConfiguration().getString(basePath + "." + permission);
                for (String playerName : getConfiguration().getStringList(permissionPath)) {
                    if (!claimPermission.containsKey(playerName)) {
                        claimPermission.put(playerName, Arrays.asList(ClaimPermission.valueOf(permission)));
                    } else {
                        claimPermission.get(playerName).add(ClaimPermission.valueOf(permission));
                    }
                }
            }

            Claim claim = new Claim(claimIdentifier, territory);
            for (String playerName : claimPermission.keySet()) {
                ClaimPlayer claimPlayer = new ClaimPlayer(playerName, claimPermission.get(playerName));
                claim.addPlayer(claimPlayer);
            }
            toReturn.add(claim);
        }
        return toReturn;
    }
}
