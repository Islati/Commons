package com.devsteady.onyx.block;

public enum Direction {
    NORTH(1, 0),
    SOUTH(-1, 0),
    EAST(0, 1),
    WEST(0, -1),
    NORTHEAST(1, 1),
    SOUTHEAST(-1, 1),
    NORTHWEST(1, -1),
    SOUTHWEST(-1, -1);

    private int x;
    private int z;

    Direction(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public static Direction getDirection(String direction) {
        for (Direction dir : Direction.values()) {
            if (dir.name().equalsIgnoreCase(direction)) {
                return dir;
            }
        }
        return null;
    }

    public static Direction getRightOf(Direction dir) {
        switch (dir) {
            case NORTH:
            case NORTHEAST:
            case NORTHWEST:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
            case SOUTHWEST:
            case SOUTHEAST:
                return WEST;
            case WEST:
                return NORTH;
            default:
                return null;
        }
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}