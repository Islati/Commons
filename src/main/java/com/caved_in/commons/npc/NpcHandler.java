package com.caved_in.commons.npc;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.packet.PacketFactory;
import com.caved_in.commons.player.PlayerInjector;
import com.caved_in.commons.player.Players;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NpcHandler {
	private static NpcHandler INSTANCE = null;

	private BiMap<Integer, EntityHuman> LOOKUP = HashBiMap.create();

	int nextID = Integer.MIN_VALUE;

	public static NpcHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NpcHandler();
		}
		return INSTANCE;
	}

	public void startUp() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerInjector.injectPlayer(player);
		}
	}

	public void shutdown() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerInjector.uninjectPlayer(player);
		}
	}

	public NPC spawnNPC(Location location, String name) {
		int id = getNextID();

		if (name.length() > 16) {
			Commons.messageConsole(Messages.NPC_NAME_LIMIT_REACHED);
			String tmp = name.substring(0, 16);
			Commons.messageConsole(Messages.npcNameShortened(name, tmp));
			name = tmp;
		}

		EntityHuman human = new EntityHuman(location, name, id);
		LOOKUP.put(id, human);

		updateNPC(human);
		return human;
	}

	public boolean isNPC(int id) {
		return LOOKUP.containsKey(id);
	}

	public NPC getNPC(int id) {
		if (isNPC(id)) {
			return LOOKUP.get(id);
		} else {
			Commons.messageConsole(Messages.invalidNpcId(id));
			return null;
		}
	}

	protected int getNextID() {
		int id = +nextID++;
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getEntityId() != id) {
					return id;
				} else {
					return getNextID();
				}
			}
		}
		return id;
	}

	public void updatePlayer(Player player) {
		for (NPC npc : LOOKUP.values()) {
			if (npc.getLocation().getWorld().equals(player.getWorld())) {
				Players.sendPacket(player, PacketFactory.craftSpawnPacket(npc));

				if (!PacketFactory.craftEquipmentPacket(npc).isEmpty()) {
					for (Object packet : PacketFactory.craftEquipmentPacket(npc)) {
						Players.sendPacket(player, packet);
					}
				}

				if (npc.isSleeping()) {
					Players.sendPacket(player, PacketFactory.craftSleepPacket(npc));
				}
			}
		}
	}

	public void updateNPC(NPC npc) {
		updateNPC(npc, PacketFactory.craftSpawnPacket(npc));

		for (Object packet : PacketFactory.craftEquipmentPacket(npc)) {
			updateNPC(npc, packet);
		}
	}

	public void updateNPC(NPC npc, Object packet) {
		if (packet != null) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getWorld().equals(npc.getLocation().getWorld())) {
					Players.sendPacket(player, packet);
				}
			}
		}
	}

	public void despawn(NPC npc) {
		npc.despawn();
		LOOKUP.inverse().remove(npc);
	}

	public void despawn(int id) {
		LOOKUP.get(id).despawn();
	}

	public BiMap<Integer, EntityHuman> getLookup() {
		return LOOKUP;
	}
}
