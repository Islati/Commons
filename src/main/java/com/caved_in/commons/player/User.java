package com.caved_in.commons.player;

import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;

import java.util.UUID;

public abstract class User implements PlayerWrapper {
	@Element(name = "name")
	private String name;

	private UUID id;

	@Element(name = "uuid")
	private String uid;

	public User(Player p) {
		name = p.getName();
		id = p.getUniqueId();
	}

	public User(@Element(name = "name") String name, @Element(name = "uuid") String uid) {
		this.name = name;
		this.uid = uid;
		id = UUID.fromString(this.uid);
	}

	public User() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(UUID id) {
		this.id = id;
		uid = id.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public Player getPlayer() {
		return Players.getPlayer(id);
	}

	/**
	 * Whether or not the player is online
	 *
	 * @return true if the player is online, false otherwise
	 */
	public boolean isOnline() {
		return Players.isOnline(getId());
	}
}
