package com.devsteady.onyx.entity;

import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.ToString;
import org.bukkit.Location;

@ToString(exclude = {"spawner"}, callSuper = true)
public class MobSpawnData extends YamlConfig {

    @Path("mob")
    private MobData mobData;

    @Path("location")
    private Location location = null;

    @Skip
    private CreatureBuilder spawner = null;

    public MobSpawnData(MobData data, Location loc) {
        this.mobData = data;
        this.location = loc;
    }

    public void spawn(int amount) {
        for (int i = 0; i < amount; i++) {
            spawn();
        }
    }

    public void spawn() {
        spawner().spawn(location);
    }

    private CreatureBuilder spawner() {
        if (spawner == null) {
            spawner = mobData.toBuilder();
        }
        return spawner;
    }

    public MobData getMobData() {
        return mobData;
    }

    public void setMobData(MobData mobData) {
        this.mobData = mobData;
    }

}
