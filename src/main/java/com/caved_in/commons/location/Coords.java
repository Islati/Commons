package com.caved_in.commons.location;

public class Coords {
    private double[] xyz;

    public Coords(double x, double y, double z) {
        this.xyz = new double[]{x, y, z};
    }

    public Coords(double[] xyz) {
        this.xyz = xyz;
    }

    public double getX() {
        return xyz[0];
    }

    public double getY() {
        return xyz[1];
    }

    public double getZ() {
        return xyz[2];
    }

    public double[] getPos() {
        return xyz;
    }
}
