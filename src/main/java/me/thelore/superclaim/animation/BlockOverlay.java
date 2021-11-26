package me.thelore.superclaim.animation;

import com.google.common.collect.Lists;
import me.thelore.superclaim.SuperClaim;
import me.thelore.superclaim.task.AsyncTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Locale;

public class BlockOverlay {
    private BukkitTask scheduler;

    private final double distance;

    private final Player player;
    private Location p1;
    private Location p2;

    public BlockOverlay(Player player, double distance) {
        this.distance = distance;
        this.player = player;
    }

    public void setP1(Location p1) {
        this.p1 = p1;
    }

    public void setP2(Location p2) {
        this.p2 = p2;
    }

    public void start() {
        scheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(SuperClaim.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(p1 == null && p2 == null) {
                    return;
                }

                if(p1 != null && p2 == null) {
                    for(Location location : outline(p1, p1, distance)) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);
                        player.spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
                    }
                }

                if(p2 != null && p1 == null) {
                    for(Location location : outline(p2, p2, distance)) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);
                        player.spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
                    }
                }

                if(p1 != null && p2 != null) {
                    for(Location location : outline(getLocs(p1, p2)[0], getLocs(p1, p2)[1], distance)) {
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1.0F);
                        player.spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
                    }
                }
            }
        }, 10, 10);
    }

    public void stop() {
        Bukkit.getScheduler().runTaskLater(SuperClaim.getInstance(), new Runnable() {
            @Override
            public void run() {
                scheduler.cancel();
            }
        }, 20 * 2);
    }

    private Location[] getLocs(Location loc1, Location loc2) {
        Location[] minmaxLoc = new Location[2];

        World world = loc1.getWorld();

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        Location minLoc = new Location(world, minX, minY, minZ);
        Location maxLoc = new Location(world, maxX, maxY, maxZ);

        minmaxLoc[0] = minLoc;
        minmaxLoc[1] = maxLoc;

        return minmaxLoc;
    }

    private List<Location> outline(Location start, Location end, double particleDistance) {
        List<Location> result = Lists.newArrayList();
        World world = start.getWorld();

        double minX = start.getBlockX();
        double minY = start.getBlockY();
        double minZ = start.getBlockZ();

        double maxX = end.getBlockX() + 1;
        double maxY = end.getBlockY() + 1;
        double maxZ = end.getBlockZ() + 1;

        for(double x = minX; x <= maxX; x += particleDistance) {
            for(double y = minY; y <= maxY; y += particleDistance) {
                for(double z = minZ; z <= maxZ; z += particleDistance) {
                    int coinditions = 0;

                    if(x == minX || x == maxX) coinditions++;
                    if(y == minY || y == maxY) coinditions++;
                    if(z == minZ || z == maxZ) coinditions++;

                    if(coinditions >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }
}
