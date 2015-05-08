package com.caved_in.commons.item;

import com.caved_in.commons.utilities.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public enum StainedClay {
    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHT_GREY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);

    private int dataValue;
    private MaterialData data;

    StainedClay(int dataValue) {
        this.dataValue = dataValue;
        data = Items.getMaterialData(Material.STAINED_CLAY, dataValue);
    }

    public MaterialData getMaterialData() {
        return data;
    }

    public static StainedClay getRandom() {
        return ArrayUtils.getRandom(StainedClay.values());
    }
}
