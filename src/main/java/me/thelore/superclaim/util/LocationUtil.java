package me.thelore.superclaim.util;

import me.thelore.superclaim.claim.Territory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {
    public static String serialize(Location location) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return world.getName() + "," + x + "," + y + "," + z;
    }

    public static Location deserialize(String location) {
        String[] args = location.split(",");

        String worldName = args[0];
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);

        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public static Location[] getLocations(Territory territory) {
        Location[] toReturn = new Location[2];

        World world = Bukkit.getWorld(territory.getWorldName());

        Location minLocation =
                new Location(world, territory.getMinX(), territory.getMinY(), territory.getMinZ());

        Location maxLocation =
                new Location(world, territory.getMaxX(), territory.getMaxY(), territory.getMaxZ());

        toReturn[0] = minLocation;
        toReturn[1] = maxLocation;

        return toReturn;
    }
}
