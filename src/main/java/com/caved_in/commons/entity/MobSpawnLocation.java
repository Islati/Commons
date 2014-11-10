package com.caved_in.commons.entity;

import com.caved_in.commons.config.XmlLocation;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.simpleframework.xml.Element;

@ToString(exclude = {"spawner"}, callSuper = true)
public class MobSpawnLocation extends XmlLocation {

	@Element(name = "spawn-chance")
	private int spawnChance = 100;

	@Element(name = "mob-data", type = MobSpawnData.class)
	private MobSpawnData mobSpawnData = new MobSpawnData();

	private CreatureBuilder spawner = null;

	public MobSpawnLocation(Location location, EntityType type) {
		super(location);
		mobSpawnData.setEntityType(type);
	}

	public MobSpawnLocation(@Element(name = "world") String worldName, @Element(name = "x-pos") double x, @Element(name = "y-pos") double y, @Element(name = "z-pos") double z, @Element(name = "pitch", required = false) float pitch, @Element(name = "yaw", required = false) float yaw, @Element(name = "mob-data", type = MobSpawnData.class) MobSpawnData data, @Element(name = "spawn-chance") int spawnChance) {
		super(worldName, x, y, z, pitch, yaw);
		this.mobSpawnData = data;
		this.spawnChance = spawnChance;
	}

	public void spawn(int amount) {
		for (int i = 0; i < amount; i++) {
			spawn();
		}
	}

	public void spawn() {
		spawner().spawn(this);
	}

	private CreatureBuilder spawner() {
		if (spawner == null) {
			spawner = mobSpawnData.toBuilder();
		}
		return spawner;
	}

	public MobSpawnData getMobSpawnData() {
		return mobSpawnData;
	}

	public void setMobSpawnData(MobSpawnData mobSpawnData) {
		this.mobSpawnData = mobSpawnData;
	}

	public int getSpawnChance() {
		return spawnChance;
	}

	public void setSpawnChance(int spawnChance) {
		this.spawnChance = spawnChance;
	}
}
