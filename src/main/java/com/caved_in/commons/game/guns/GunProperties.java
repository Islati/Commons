package com.caved_in.commons.game.guns;

import com.caved_in.commons.game.item.WeaponProperties;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;

import com.caved_in.commons.yml.Path;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class GunProperties extends WeaponProperties {
    /**
     * The size of our launchers clip. After 20 shots, they'll need to reload.
     */
    @Path("clip-size")
    public int clipSize = 20;

    @Path("shot-delay")
    public long shotDelay = 1500l;

    @Path("reload-speed")
    public int reloadSpeed = 5;

    @Path("reload-message")
    public boolean reloadMessage = true;

    @Path("display-ammo")
    public boolean displayAmmo = true;

    @Path("range")
    public int range = 100;

    /**
     * What ammunition to use for the guns. By default it's flint.
     */
    @Path("ammunition")
    private ItemStack ammunition = Items.makeItem(Material.FLINT);

    /**
     * How many bullets each shot takes.
     */
    @Path("rounds-per-shot")
    public int roundsPerShot = 1;

    @Path("cluster-shot")
    public boolean clusterShot = false;

    @Path("take-ammunition-on-fire")
    public boolean takeAmmunition = true;

    private Gun parent;

    public GunProperties() {
        droppable(false);
        breakable(false);
    }

    public GunProperties(Gun parent) {
        /* Call the default gun initializer, to set both the dropping and breaking to false) */
        this();

        this.parent = parent;
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
     *
     * @param seconds amount of seconds it takes the gun to reload.
     * @return the gun-properties builder.
     */
    public GunProperties reloadSpeed(int seconds) {
        return reloadSpeed(seconds, TimeType.SECOND);
    }

    /**
     * Set how many bullets are shot per round.
     * <p>
     * If the cluster shot variable is set, all of the rounds will
     * be shot at once.
     * <p>
     * If not, then each round will be shot at a rate that correlates to the
     * delay between shots variable.
     *
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
     *
     * @param item item that this gun will shoot.
     * @return the gun-properties builder.
     */
    public GunProperties ammunition(ItemStack item) {
        this.ammunition = item.clone();
        return this;
    }

    public GunProperties ammunition(ItemBuilder builder) {
        ammunition(builder.item());
        return this;
    }

    /**
     * Set the delay in how often this gun can be shot (in milleseconds)
     *
     * @param millis delay in how often the gun can be shot (in milleseconds)
     * @return the gun-properties builder.
     */
    public GunProperties shotDelay(long millis) {
        this.shotDelay = millis;
        return this;
    }

    /**
     * Whether or not to shoot the gun in cluster shots.
     *
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
     *
     * @param val whether or not to display the amount of ammo on the gun.
     * @return the gun-properties builder.
     */
    public GunProperties displayAmmo(boolean val) {
        this.displayAmmo = val;
        return this;
    }

    public GunProperties takeAmmunition(boolean val) {
        this.takeAmmunition = val;
        return this;
    }

    /**
     * Set how much damage the gun will do before applying bullet damage!
     * Overall damage for the gun is calculated on the gun damage plus the bullet damage
     *
     * @param amt amount of damage this gun will do before applying bullet damage.
     * @return the gun-properties builder
     */
    public GunProperties damage(double amt) {
        damage(amt, amt);
        return this;
    }

    public ItemStack ammunition() {
        return ammunition;
    }

    public Gun parent() {
        return parent;
    }
}
