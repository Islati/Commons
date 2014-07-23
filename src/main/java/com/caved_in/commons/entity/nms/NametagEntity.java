package com.caved_in.commons.entity.nms;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityAmbient;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

public class NametagEntity extends EntityAmbient {
	public NametagEntity(Player player) {
		super(((CraftWorld) player.getWorld()).getHandle());

		Location location = player.getLocation();

		setInvisible(true);
		setPosition(location.getX(), location.getY(), location.getZ());

		try {
			Field invulnerable = Entity.class.getDeclaredField("invulnerable");
			invulnerable.setAccessible(true);
			invulnerable.setBoolean(this, true);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);

		this.persistent = true;
	}

	public void hideTag(Player player) {
		setPassengerOf(((CraftPlayer) player).getHandle());
	}

	public void showTag() {
		setPassengerOf(null);
	}

	public void h() {
		this.motX = (this.motY = this.motZ = 0.0D);
		a(0.0F, 0.0F);
		a(0.0F, 0.0F, 0.0F);
	}

	public void o(Entity entity) {

	}
}
