package com.caved_in.commons.location;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;


public class Locations {

    private static final double TRUE_CIRLE = 0.5;
    private static final int FALSE_CIRCLE = 0;

    public static boolean USE_TRUE_CIRCLE = false;

    public static Location getRandomLocationInArea(Location loc1, Location loc2) {
        if (!loc1.getWorld().getName().equals(loc2.getWorld().getName())) {
            return null;
        }

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), NumberUtil.randomDouble(minX, maxX), NumberUtil.randomDouble(minY, maxY), NumberUtil.randomDouble(minZ, maxZ));
    }

    public static boolean isInsideArea(Location loc, Location areaPointA, Location areaPointB, boolean checkY) {
        String mainLocWorldName = loc.getWorld().getName();
        //If they're not all in the same world then we don't check.
        if (!mainLocWorldName.equals(areaPointA.getWorld().getName()) || !mainLocWorldName.equals(areaPointB.getWorld().getName())) {
            return false;
        }

        //Check the X Coordinate.
        if ((loc.getBlockX() >= areaPointA.getBlockX() && loc.getBlockX() <= areaPointB.getBlockX()) || (loc.getBlockX() <= areaPointA.getBlockX() && loc.getBlockX() >= areaPointB.getBlockX())) {
            //Check the Z
            if ((loc.getBlockZ() >= areaPointA.getBlockZ() && loc.getBlockZ() <= areaPointB.getBlockZ()) || (loc.getBlockZ() <= areaPointA.getBlockZ() && loc.getBlockZ() >= areaPointB.getBlockZ())) {

                /* If we're not checking the y coordinate then it's inside the loc */
                if (!checkY) {
                    return true;
                }

                /* Otherwise return the results from the final check as the result. */
                return ((loc.getBlockY() >= areaPointA.getBlockY() && loc.getBlockY() <= areaPointB.getBlockY()) || (loc.getBlockY() <= areaPointA.getBlockY() && loc.getBlockY() >= areaPointB.getBlockY()));

            }
        }
        return false;
    }

    public static Set<Player> getPlayersInRadius(Location location, double radius) {
        Set<Player> playerInRadius = new HashSet<>();
        Collection<Player> onlinePlayers = Players.allPlayers();
        double radiusSquared = radius * radius;
        World centerWorld = location.getWorld();
        for (Player onlinePlayer : onlinePlayers) {
            Location playerLoc = onlinePlayer.getLocation();

            if (!playerLoc.getWorld().equals(centerWorld)) {
                continue;
            }

            if (playerLoc.distanceSquared(location) <= radiusSquared) {
                playerInRadius.add(onlinePlayer);
            }
        }
        return playerInRadius;
    }

    public static boolean isPlayerInRadius(Location location, double radius, Player player) {
        return isEntityInRadius(location, radius, player);
    }

    public static boolean isEntityInRadius(Location center, double radius, Entity entity) {
        return isInRadius(center, entity.getLocation(), radius);
    }

    public static boolean isInRadius(Location center, Location loc, double radius) {
        /*
		If the world of the 2 locations isn't the same,
		then they're clearly not in the radius!
		 */
        if (!loc.getWorld().equals(center.getWorld())) {
            return false;
        }

        return center.distanceSquared(loc) <= (radius * radius);
    }

    public static Location getRandomLocation(Location locationCenter, double radius) {
        Random rand = new Random();
        double angle = rand.nextDouble() * 360; //Generate a random angle
        double x = locationCenter.getX() + (rand.nextDouble() * radius * Math.cos(Math.toRadians(angle)));
        double z = locationCenter.getZ() + (rand.nextDouble() * radius * Math.sin(Math.toRadians(angle)));
        double y = locationCenter.getWorld().getHighestBlockYAt((int) x, (int) z);
        return new Location(locationCenter.getWorld(), x, y, z);
    }

    public static int[] getXYZ(Location location) {
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();
        return new int[]{x, y, z};
    }

    public static Location getLocation(World world, double x, double y, double z) {
        return new Location(world, x, y, z);
    }

    public static Location getLocation(String worldName, double x, double y, double z) {
        return getLocation(Worlds.getWorld(worldName), x, y, z);
    }

    public static Location getLocation(String worldName, double[] xyz) {
        return getLocation(worldName, xyz[0], xyz[1], xyz[2]);
    }

    public static Location getLocation(World world, double[] xyz) {
        return new Location(world, xyz[0], xyz[1], xyz[2]);
    }

    public static Location getNormalizedLocation(Location location) {
        return getLocation(location.getWorld(), location.getX(), location.getY() + 1, location.getZ());
    }

    public static Location getRoundedCompassLocation(Location location, int round) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        z = Math.round(z / round) * round;
        x = Math.round(x / round) * round;
        return new Location(location.getWorld(), x, 0.0, z);
    }

    public static List<Location> getParticlesCircle(Location center, float radius, float distanceBetweenParticles) {
        List<Location> locs = new ArrayList<>();
        for (float i = 0F; i < 360F; ) {
            locs.add(new Location(center.getWorld(), center.getX() + Math.cos((double) i) / radius, center.getY(), center.getZ() + Math.sin((double) i) / radius));
            i = i + distanceBetweenParticles;
        }
        return locs;
    }

    public static List<Location> getSpiral(Location center, Float degrees, double centerRadius, float radius, float distanceBetweenParticles) {
        List<Location> locs = new ArrayList<>();
        for (float i = 0F; i < degrees; ) {
            locs.add(new Location(center.getWorld(), center.getX() + Math.sin((double) i) / radius, center.getY() + i / centerRadius, center.getZ() + Math.cos((double) i) / radius));
            i = i + distanceBetweenParticles;
        }
        return locs;
    }


    /**
     * Get a full circle (not just the parameter) around the radius!
     *
     * @param center center of the circle selection.
     * @param radius radius of the circle
     * @return a list of locations that were in the circle.
     */
    public static List<Location> getFullCircle(Location center, int radius) {
        List<Location> locs = new ArrayList<>();

        World world = center.getWorld();


        double circleSize = USE_TRUE_CIRCLE ? TRUE_CIRLE : FALSE_CIRCLE;
        final double radiusSquared = (radius + circleSize) * (radius * circleSize);

        final Vector centerPoint = center.toVector();
        final Vector currentPoint = centerPoint.clone();


        for (int x = -radius; x <= radius; x++) {
            currentPoint.setX(centerPoint.getX() + x);

            for (int z = -radius; z <= radius; z++) {
                currentPoint.setZ(centerPoint.getZ() + z);

                //If the point is within the bounds of the radius, then it's part of the circle!
                if (centerPoint.distanceSquared(currentPoint) <= radiusSquared) {
                    locs.add(currentPoint.toLocation(world));
                }
            }
        }

        return locs;
    }

    /**
     * Returns a list of all the blocks in a circle within a certain radius of a location.
     * <p>
     * <p>Author: ArthurMaker</p>
     *
     * @param centerLoc center Location
     * @param radius    radius of the circle
     * @return list of blocks
     */
    public static List<Location> getCircle(Location centerLoc, int radius) {
        List<Location> circle = new ArrayList<>();
        World world = centerLoc.getWorld();
        int x = 0;
        int z = radius;
        int error = 0;
        int d = 2 - 2 * radius;
        while (z >= 0) {
            circle.add(new Location(world, centerLoc.getBlockX() + x, centerLoc.getY(), centerLoc.getBlockZ() + z));
            circle.add(new Location(world, centerLoc.getBlockX() - x, centerLoc.getY(), centerLoc.getBlockZ() + z));
            circle.add(new Location(world, centerLoc.getBlockX() - x, centerLoc.getY(), centerLoc.getBlockZ() - z));
            circle.add(new Location(world, centerLoc.getBlockX() + x, centerLoc.getY(), centerLoc.getBlockZ() - z));
            error = 2 * (d + z) - 1;
            if ((d < 0) && (error <= 0)) {
                x++;
                d += 2 * x + 1;
            } else {
                error = 2 * (d - x) - 1;
                if ((d > 0) && (error > 0)) {
                    z--;
                    d += 1 - 2 * z;
                } else {
                    x++;
                    d += 2 * (x - z);
                    z--;
                }
            }
        }
        return circle;
    }


    public static List<Location> getPlain(Location position1, Location position2) {
        List<Location> plain = new ArrayList<>();
        if (position1 == null) {
            return plain;
        }
        if (position2 == null) {
            return plain;
        }
        for (int x = Math.min(position1.getBlockX(), position2.getBlockX()); x <= Math.max(position1.getBlockX(), position2.getBlockX()); x++) {
            for (int z = Math.min(position1.getBlockZ(), position2.getBlockZ()); z <= Math.max(position1.getBlockZ(), position2.getBlockZ()); z++) {
                plain.add(new Location(position1.getWorld(), x, position1.getBlockY(), z));
            }
        }
        return plain;
    }

    /**
     * Returns a list of all the blocks in a diagonal line between two locations.
     * <p>
     * <p>ArthurMaker</p>
     *
     * @param position1 first position
     * @param position2 second position
     * @return list of blocks
     */
    public static List<Location> getLine(Location position1, Location position2) {
        List<Location> line = new ArrayList<>();
        int dx = Math.max(position1.getBlockX(), position2.getBlockX()) - Math.min(position1.getBlockX(), position2.getBlockX());
        int dy = Math.max(position1.getBlockY(), position2.getBlockY()) - Math.min(position1.getBlockY(), position2.getBlockY());
        int dz = Math.max(position1.getBlockZ(), position2.getBlockZ()) - Math.min(position1.getBlockZ(), position2.getBlockZ());
        int x1 = position1.getBlockX();
        int x2 = position2.getBlockX();
        int y1 = position1.getBlockY();
        int y2 = position2.getBlockY();
        int z1 = position1.getBlockZ();
        int z2 = position2.getBlockZ();
        int x = 0;
        int y = 0;
        int z = 0;
        int i = 0;
        int d = 1;
        switch (getHighest(dx, dy, dz)) {
            case 1:
                i = 0;
                d = 1;
                if (x1 > x2) {
                    d = -1;
                }
                x = position1.getBlockX();
                do {
                    i++;
                    y = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
                    z = z1 + (x - x1) * (z2 - z1) / (x2 - x1);
                    line.add(new Location(position1.getWorld(), x, y, z));
                    x += d;
                } while (i <= Math.max(x1, x2) - Math.min(x1, x2));
                break;
            case 2:
                i = 0;
                d = 1;
                if (y1 > y2) {
                    d = -1;
                }
                y = position1.getBlockY();
                do {
                    i++;
                    x = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                    z = z1 + (y - y1) * (z2 - z1) / (y2 - y1);
                    line.add(new Location(position1.getWorld(), x, y, z));
                    y += d;
                } while (i <= Math.max(y1, y2) - Math.min(y1, y2));
                break;
            case 3:
                i = 0;
                d = 1;
                if (z1 > z2) {
                    d = -1;
                }
                z = position1.getBlockZ();
                do {
                    i++;
                    y = y1 + (z - z1) * (y2 - y1) / (z2 - z1);
                    x = x1 + (z - z1) * (x2 - x1) / (z2 - z1);
                    line.add(new Location(position1.getWorld(), x, y, z));
                    z += d;
                } while (i <= Math.max(z1, z2) - Math.min(z1, z2));
        }
        return line;
    }

    private static int getHighest(int x, int y, int z) {
        if ((x >= y) && (x >= z)) {
            return 1;
        }
        if ((y >= x) && (y >= z)) {
            return 2;
        }
        return 3;
    }

    public static String getWorldName(Location location) {
        return location.getWorld().getName();
    }

    public static boolean inSameWorld(Location loc, Location check) {
        return loc.getWorld().equals(check);
    }

    public static boolean isBehind(LivingEntity entityToCheck, LivingEntity entityBehind) {
        return Math.abs(Math.toDegrees(entityToCheck.getEyeLocation().getDirection().angle(entityBehind.getLocation().getDirection()))) < 45;
    }
}