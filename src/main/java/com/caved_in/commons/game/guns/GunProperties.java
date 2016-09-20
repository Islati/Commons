package com.caved_in.commons.game.guns;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.game.item.WeaponProperties;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "guns-properties")
public class GunProperties extends WeaponProperties {
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

    @Element(name = "take-ammunition-on-fire", required = false)
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

    public GunProperties(@Element(name = "durability") int durability, @Element(name = "breakable") boolean isBreakable, @Element(name = "droppable") boolean isDroppable, @Element(name = "offHandEquipable") boolean offHandEquipable, @Element(name = "damage-min") double damageMin, @Element(name = "damage-max") double damageMax, @Element(name = "clip-size") int clipSize, @Element(name = "fire-delay-millis") long shotDelay, @Element(name = "reload-speed-seconds") int reloadSpeed, @Element(name = "rounds-per-shot") int roundsPerShot, @Element(name = "ammunition", type = XmlItemStack.class) XmlItemStack ammunition, @Element(name = "cluster-shot") boolean clusterShot, @Element(name = "reload-message", required = false) boolean reloadMessage, @Element(name = "display-ammo") boolean displayAmmo, @Element(name = "take-ammunition-on-fire", required = false) boolean takeAmmunition) {
        /* The first 3 items, durability, isDroppable, and otherwise are merely placeholders! */
        super(-1, false, false, offHandEquipable, damageMin, damageMax);

        this.clipSize = clipSize;
        this.shotDelay = shotDelay;
        this.reloadSpeed = reloadSpeed;
        this.roundsPerShot = roundsPerShot;
        this.ammunition = ammunition;
        this.clusterShot = clusterShot;
        this.reloadMessage = reloadMessage;
        this.displayAmmo = displayAmmo;
        this.takeAmmunition = takeAmmunition;
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
        this.ammunition = XmlItemStack.fromItem(item.clone());
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
        return ammunition.getItemStack();
    }

    public Gun parent() {
        return parent;
    }
}
