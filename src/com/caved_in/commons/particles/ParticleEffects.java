package com.caved_in.commons.particles;

import com.caved_in.commons.location.LocationHandler;
import net.minecraft.server.v1_6_R3.Packet63WorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

//import net.minecraft.server.v1_5_R3.Packet63WorldParticles;

public enum ParticleEffects {
	HUGE_EXPLOSION("hugeexplosion"), LARGE_EXPLODE("largeexplode"), FIREWORKS_SPARK("fireworksSpark"), BUBBLE("bubble"), SUSPEND("suspend"), DEPTH_SUSPEND("depthSuspend"), TOWN_AURA("townaura"), CRIT("crit"), MAGIC_CRIT("magicCrit"), MOB_SPELL("mobSpell"), MOB_SPELL_AMBIENT("mobSpellAmbient"), SPELL("spell"), INSTANT_SPELL("instantSpell"), WITCH_MAGIC("witchMagic"), NOTE("note"), PORTAL("portal"), ENCHANTMENT_TABLE("enchantmenttable"), EXPLODE("explode"), FLAME("flame"), LAVA("lava"), FOOTSTEP("footstep"), SPLASH("splash"), LARGE_SMOKE("largesmoke"), CLOUD("cloud"), RED_DUST("reddust"), SNOWBALL_POOF("snowballpoof"), DRIP_WATER("dripWater"), DRIP_LAVA("dripLava"), SNOW_SHOVEL("snowshovel"), SLIME("slime"), HEART("heart"), ANGRY_VILLAGER("angryVillager"), HAPPY_VILLAGER("happerVillager"), ICONCRACK("iconcrack_"), TILECRACK("tilecrack_");

	private String particleName;

	private ParticleEffects(String particleName) {
		this.particleName = particleName;
	}

	private Packet63WorldParticles Packet(Location location, float offsetX, float offsetY, float offsetZ, float speed, int particleCount) throws Exception {
		Packet63WorldParticles packet = new Packet63WorldParticles();
		ReflectionUtilities.setValue(packet, "a", this.particleName);
		ReflectionUtilities.setValue(packet, "b", (float) location.getX());
		ReflectionUtilities.setValue(packet, "c", (float) location.getY());
		ReflectionUtilities.setValue(packet, "d", (float) location.getZ());
		ReflectionUtilities.setValue(packet, "e", offsetX);
		ReflectionUtilities.setValue(packet, "f", offsetY);
		ReflectionUtilities.setValue(packet, "g", offsetZ);
		ReflectionUtilities.setValue(packet, "h", speed);
		ReflectionUtilities.setValue(packet, "i", particleCount);
		return packet;
	}

	public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(Packet(location, offsetX, offsetY, offsetZ, speed, count));
	}

	public void sendToPlayer(Player Player, Location location, float speed, int Count) throws Exception {
		sendToPlayer(Player, location, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), speed, Count);
	}

	public void sendToAll(Location location, float speed, int count) throws Exception {
		for (Player player : Bukkit.getOnlinePlayers()) {
			sendToPlayer(player, location, speed, count);
		}
	}

	public void sendToAllInRadius(Location particleLocation, Location radiusLocation, double Radius, float speed, int Count) throws Exception {
		for (Player player : LocationHandler.getPlayersInRadius(radiusLocation, Radius)) {
			sendToPlayer(player, particleLocation, speed, Count);
		}
	}

	public void sendToAllInRadius(Location particleLocation, double radius, float speed, int particleCount) throws Exception {
		for (Player player : LocationHandler.getPlayersInRadius(particleLocation, radius)) {
			sendToPlayer(player, particleLocation, speed, particleCount);
		}
	}
}