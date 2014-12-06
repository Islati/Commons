package com.caved_in.commons.game.guns;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "guns-properties")
public class GunProperties {
	/**
	 * The size of our launchers clip. After 20 shots, they'll need to reload.
	 */
	@Element(name = "clip-size")
	public int clipSize = 20;

	@Element(name = "fire-delay-millis")
	public long shotDelay = 1500l;

	@Element(name = "reload-speed")
	public int reloadSpeed = 5;

	@Element(name = "reload-message")
	public boolean reloadMessage = true;

	@Element(name = "display-ammo")
	public boolean displayAmmo = true;

	@Element(name = "block-range")
	public int range = 100;

	/**
	 * What ammunition to use for the guns. By default it's flint.
	 */
	@Element(name = "ammunition", type = XmlItemStack.class)
	private XmlItemStack ammunition = XmlItemStack.fromItem(Items.makeItem(Material.FLINT));

	/**
	 * How many bullets each shot takes.
	 */
	@Element(name = "rounds-per-shot")
	public int roundsPerShot = 1;

	@Element(name = "cluster-shot")
	public boolean clusterShot = false;

	private Gun parent;

	public GunProperties() {
	}

	public GunProperties(Gun parent) {
		this.parent = parent;
	}

	public GunProperties(@Element(name = "clip-size") int clipSize, @Element(name = "fire-delay-millis") long shotDelay, @Element(name = "reload-speed-seconds") int reloadSpeed, @Element(name = "rounds-per-shot") int roundsPerShot, @Element(name = "ammunition", type = XmlItemStack.class) XmlItemStack ammunition, @Element(name = "cluster-shot") boolean clusterShot, @Element(name = "reload-message", required = false) boolean reloadMessage, @Element(name = "display-ammo") boolean displayAmmo) {
		this.clipSize = clipSize;
		this.shotDelay = shotDelay;
		this.reloadSpeed = reloadSpeed;
		this.roundsPerShot = roundsPerShot;
		this.ammunition = ammunition;
		this.clusterShot = clusterShot;
		this.reloadMessage = reloadMessage;
		this.displayAmmo = displayAmmo;
	}

	/**
	 * Set how much ammo the clip will hold.
	 *
	 * @param size how much ammo the clip will hold.
	 * @return the gun-properties builder.
	 */
	public GunProperties clipSize(int size) {
		this.clipSize = size;
		return this;
	}

	public GunProperties reloadTicks(int amt) {
		reloadSpeed = amt;
		return this;
	}


	public GunProperties reloadSpeed(int amount, TimeType type) {
		return reloadTicks((int) TimeHandler.getTimeInTicks(amount, type));
	}

	/**
	 * Set the amount of seconds it takes to reload the gun.
	 * @param seconds amount of seconds it takes the gun to reload.
	 * @return the gun-properties builder.
	 */
	public GunProperties reloadSpeed(int seconds) {
		return reloadSpeed(seconds, TimeType.SECOND);
	}

	/**
	 * Set how many bullets are shot per round.
	 *
	 * If the cluster shot variable is set, all of the rounds will
	 * be shot at once.
	 *
	 * If not, then each round will be shot at a rate that correlates to the
	 * delay between shots variable.
	 * @param amount how many rounds should be fired with each shot.
	 * @return the gun-properties builder.
	 */
	public GunProperties roundsPerShot(int amount) {
		this.roundsPerShot = amount;
		return this;
	}

	/**
	 * Set the range of how far the bullets will travel (in blocks).
	 *
	 * @param range range of the bullets (in blocks)
	 * @return the gun-properties builder.
	 */
	public GunProperties range(int range) {
		this.range = range;
		return this;
	}

	/**
	 * Set the ammunition item that this gun will shoot.
	 * @param item item that this gun will shoot.
	 * @return the gun-properties builder.
	 */
	public GunProperties ammunition(ItemStack item) {
		this.ammunition = XmlItemStack.fromItem(item.clone());
		return this;
	}

	/**
	 * Set the delay in how often this gun can be shot (in milleseconds)
	 * @param millis delay in how often the gun can be shot (in milleseconds)
	 * @return the gun-properties builder.
	 */
	public GunProperties shotDelay(long millis) {
		this.shotDelay = millis;
		return this;
	}

	/**
	 * Whether or not to shoot the gun in cluster shots.
	 * @param val Whether or not to shoot the gun in cluster shots.
	 * @return the gun-properties builder.
	 */
	public GunProperties clusterShot(boolean val) {
		this.clusterShot = val;
		return this;
	}

	/**
	 * @param val Whether or not to display a reload message upon finishing the reload.
	 * @return the gun-properties builder.
	 */
	public GunProperties reloadMessage(boolean val) {
		this.reloadMessage = val;
		return this;
	}

	/**
	 * Set whether or not to display the amount of ammo on the gun.
	 * @param val whether or not to display the amount of ammo on the gun.
	 * @return the gun-properties builder.
	 */
	public GunProperties displayAmmo(boolean val) {
		this.displayAmmo = val;
		return this;
	}

	public ItemStack ammunition() {
		return ammunition.getItemStack();
	}

	public Gun parent() {
		return parent;
	}
}
