package com.caved_in.commons.game.guns;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.item.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "guns-attributes")
public class GunProperties {
	/**
	 * The size of our launchers clip. After 20 shots, they'll need to reload.
	 */
	@Element(name = "clip-size")
	public int clipSize = 20;

	@Element(name = "shoot-delay-millis")
	public long shotDelay = 1500l;

	@Element(name = "reload-speed-seconds")
	public int reloadSpeed = 5;

	/**
	 * How many bullets each shot takes.
	 */
	@Element(name = "rounds-per-shot")
	public int roundsPerShot = 1;

	@Element(name = "cluster-shot")
	public boolean clusterShot = false;

	/**
	 * What ammunition to use for the guns. By default it's flint.
	 */
	@Element(name = "ammunition", type = XmlItemStack.class)
	private XmlItemStack ammunition = XmlItemStack.fromItem(Items.makeItem(Material.FLINT));

	private Gun parent;

	public GunProperties() {
	}

	public GunProperties(Gun parent) {
		this.parent = parent;
	}

	public GunProperties(@Element(name = "clip-size") int clipSize, @Element(name = "shoot-delay-millis") long shotDelay, @Element(name = "reload-speed-seconds") int reloadSpeed, @Element(name = "rounds-per-shot") int roundsPerShot, @Element(name = "ammunition", type = XmlItemStack.class) XmlItemStack ammunition, @Element(name = "cluster-shot") boolean clusterShot) {
		this.clipSize = clipSize;
		this.shotDelay = shotDelay;
		this.reloadSpeed = reloadSpeed;
		this.roundsPerShot = roundsPerShot;
		this.ammunition = ammunition;
		this.clusterShot = clusterShot;
	}

	public GunProperties clipSize(int size) {
		this.clipSize = size;
		return this;
	}

	public GunProperties reloadSpeed(int seconds) {
		this.reloadSpeed = seconds;
		return this;
	}

	public GunProperties roundsPerShot(int amount) {
		this.roundsPerShot = amount;
		return this;
	}

	public GunProperties ammunition(ItemStack item) {
		this.ammunition = XmlItemStack.fromItem(item.clone());
		return this;
	}

	public GunProperties shotDelay(long millis) {
		this.shotDelay = millis;
		return this;
	}

	public GunProperties clusterShot(boolean val) {
		this.clusterShot = val;
		return this;
	}

	public ItemStack ammunition() {
		return ammunition.getItemStack();
	}

	public Gun parent() {
		return parent;
	}
}
