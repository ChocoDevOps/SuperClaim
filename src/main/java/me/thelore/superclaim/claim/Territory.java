package me.thelore.superclaim.claim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Territory {
    private String worldName;

    private int minX;
    private int minY;
    private int minZ;

    private int maxX;
    private int maxY;
    private int maxZ;

    public boolean comprehend(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Objects.requireNonNull(location.getWorld(), "World cannot be null");
        if(!worldName.equals(location.getWorld().getName())) {
            return false;
        }

        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public int getBlocks() {
        int xDiff = (maxX - minX) + 1;
        int zDiff = (maxZ - minZ) + 1;

        return xDiff * zDiff;
    }
}
