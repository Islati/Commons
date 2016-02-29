package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Position extends Config {
    private int x;
    private int y;
    private int z;

    public Position() {
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
