package com.devsteady.onyx.game.guns;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.entity.Entities;
import com.devsteady.onyx.exceptions.ProjectileCreationException;
import com.devsteady.onyx.game.gadget.ItemGadget;
import com.devsteady.onyx.inventory.Inventories;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.player.OnyxPlayer;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * An absolutely spectacular extension of the {@link ItemGadget} class, which implements the behaviours of a Gun.
 *
 * Through usage of the {@link BaseBullet} (and chosen implementation) it fires a bullet whenever the gadget is interacted with.
 *
 * Guns also contain a per-player ammo variable, and handles reloading whenever the ammo reaches 0.
 *
 * Further development will feature options of removing an itemstack from the players inventory based on the item attached
 * to the "equipped" (associated) bullet, Optionally.
 */
public abstract class BaseGun extends ItemGadget implements Gun {
    private static final Onyx onyx = Onyx.getInstance();
    private static final Random random = new Random();

    @Path("item")
    private ItemStack gun;

    @Skip
    /* Used internally to put the ammo count in a guns name (display) */
    private String gunBaseName;

    @Path("properties")
    private GunProperties properties = new GunProperties();

    @Path("bullet-properties")
    private BulletProperties bullets = new BulletProperties();

    @Skip
    private Map<UUID, Integer> ammoCounts = new HashMap<>();

    private Map<UUID, Long> shootDelays = new HashMap<>();

    private BulletBuilder builder = null;

    private BulletActions actions;

    public BaseGun(ItemStack gun, GunProperties properties, BulletProperties bulletProperties) {
        super(gun);
        this.gun = gun;
        this.gunBaseName = Items.getName(gun);
        this.properties = properties;
        this.bullets = bulletProperties;
    }

    public BaseGun(ItemStack item) {
        super(item);
        gun = item.clone();
        this.gunBaseName = Items.getName(gun);
    }

    public BaseGun(ItemBuilder builder) {
        super(builder);
        gun = getItem();
        this.gunBaseName = Items.getName(gun);
    }

    private void initBuilder() {
        if (builder == null) {
            builder = new BulletBuilder(properties.ammunition()).gun(this);
        }

        builder.damage(damage()).power(bullets.speed);
    }

    @Override
    public void perform(Player holder) {
        initBuilder();

		/*
        If the player's on cooldown from
		using this gun, then don't let them fire.
		 */
        if (isOnCooldown(holder)) {
            return;
        }

		/*
		If the gadget the player has needs
		to be reloaded then attempt to do so.
		 */
        if (needsReload(holder)) {
			/*
			If the gadget was reloaded then send the
			player a message saying it was, but via
			the neat-0 itemMessage system.
			 */
            if (!reload(holder)) {
                return;
            }
        }

        int roundsToShoot = getRoundsToShoot(holder);

        boolean scheduleReload = false;

        //We need to fire atleast one bullet.
        if (roundsToShoot < properties.roundsPerShot) {
            scheduleReload = true;
        }

		/*
		If we don't need to schedule a reload, then subtract the ammo
		from the players total ammo count!.
		 */
        if (!scheduleReload) {

            int ammoAfterShooting = getAmmo(holder);

			/*
			If the bullets are in a cluster-shot (aka; shells, like a shotgun)
			then we only want to subtract one round from the total!

			If they're not in a cluster shot, then they
			could be shooting in rapid succession;
			like 3-burst and 4-burst weapons.
			 */
            if (properties.clusterShot) {
                ammoAfterShooting -= 1;
            } else {
                ammoAfterShooting -= roundsToShoot;
            }

            setAmmo(holder, ammoAfterShooting);
        }

        //Assign the shooter to the builder, used in the vector calculations.
        builder = builder.shooter(holder);

        if (properties.clusterShot) {
			/*
			Cluster shot means it's like a shotgun,
			where the bullets / items all come out at once.
			 */
            for (int i = 0; i < roundsToShoot; i++) {
                try {
                    builder.shoot();
                } catch (ProjectileCreationException e) {
                    e.printStackTrace();
                }
            }
        } else {
			/*
			Because we're not using cluster shot,
			we're going to delay the time between each shot,
			so it looks like burst fire.
			 */
            for (int i = 0; i < roundsToShoot; i++) {
				/*
				Schedule each bullet to be fired with
				the given delay, otherwise they'd be in a cluster.
				 */
                Onyx.getInstance().getThreadManager().runTaskLater(() -> {
                    try {
						/*
						Apply new spread to the projectile gun,
						and then fire that m'fucka to space and back.
						 */
                        builder.shoot();
                    } catch (ProjectileCreationException e) {
                        e.printStackTrace();
                    }
                }, i * bullets.delay);
            }
        }


		/*
		Set the player on cooldown from using this weapon.
		 */
        addCooldown(holder);

		/*
		Handle the on-fire of the gun, what the item's meant to do.
		 */
        onFire(holder);

        if (scheduleReload || getAmmo(holder) == 0) {
            reload(holder);
        }
    }

    @Override
    public void onDrop(Player p, Item item) {
        reload(p);
    }

    public boolean reload(final Player player) {

        UUID id = player.getUniqueId();

        final OnyxPlayer mcPlayer = onyx.getPlayerHandler().getUser(id);

		/*
		If the player's already reloading,
		then don't bother with reloading again.
		 */
        if (mcPlayer.isReloading()) {
            return false;
        }

        mcPlayer.setReloading(properties.reloadSpeed);

		/*
		Reload according to the guns reload speed!
		 */
        onyx.getThreadManager().runTaskLater(() -> {

			/*
			If the player reloading isn't online anymore then we're going to cancel.
			 */
            if (!Players.isOnline(id)) {
                return;
            }

            if (ammoCounts.containsKey(id)) {
				/*
				If this gun has reload messages,
				then send the message saying the
				gun was reloaded.
				 */
                if (properties.reloadMessage) {
                    Chat.message(player, Messages.gadgetReloaded(this));
                }
            }

			
			/*
			If the gun actually takes the ammunition from the player, then
			assure it gets taken from their inventory.
			 */
            if (properties.takeAmmunition) {
                //todo check if player ammo count < clip size, if so, partially fill clip.
            }

            setAmmo(player, properties.clipSize);

			/*
			The player's no-longer reloading,
			so remove them from doing so.
			 */
            mcPlayer.setReloading(0);
        }, properties.reloadSpeed);
        return true;
    }

    private void addCooldown(Player player) {
        shootDelays.put(player.getUniqueId(), System.currentTimeMillis() + properties.shotDelay);
    }

    private boolean isOnCooldown(Player player) {
        UUID id = player.getUniqueId();
        if (!shootDelays.containsKey(id)) {
            return false;
        }

        return shootDelays.get(id) >= System.currentTimeMillis();
    }

    public boolean needsReload(Player player) {
        UUID id = player.getUniqueId();

        if (!ammoCounts.containsKey(id)) {
            return false;
        }

        int ammoCount = ammoCounts.get(id);

        return ammoCount <= 0;
    }

    public void damage(LivingEntity damaged, Player shooter) {
        Entities.damage(damaged, damage(), shooter);
        actions.onHit(shooter, damaged);
    }

    @Override
    public BulletActions getBulletActions() {
        return actions;
    }

    @Override
    public void setBulletActions(BulletActions actions) {
        this.actions = actions;
    }

    @Override
    public GunProperties properties() {
        return properties;
    }

    public void properties(GunProperties properties) {
        this.properties = properties;
    }

    /**
     * Retrieve the amount of ammo the player currently has for the gun.
     * @param player player to get the ammo for.
     * @return amount of ammo the player currently has for the gun.
     */
    public int getAmmo(Player player) {
        UUID id = player.getUniqueId();
        if (!ammoCounts.containsKey(id)) {
            ammoCounts.put(id, properties.clipSize);
        }
        return ammoCounts.get(player.getUniqueId());
    }

    /**
     * Set the amount of ammo the player has for this gun.
     * @param player player to change the ammo amount for.
     * @param amt amount of ammo to assign the player.
     */
    public void setAmmo(Player player, int amt) {
        ammoCounts.put(player.getUniqueId(), amt);

        if (properties.displayAmmo) {
            giveGunAmmoCount(player);
        }
    }

    private void giveGunAmmoCount(Player player) {
        int slot = Inventories.getSlotOf(player.getInventory(), gun.getType(), Items.getName(gun));

        if (slot == -1) {
            onyx.debug("Unable to get slot of " + getItemName() + " on player " + player.getName());
            return;
        }

        ItemStack item = Players.getItem(player, slot);
        Items.setName(item, Messages.gunNameAmmoFormat(getItemName(), getAmmo(player)));
    }

    @Override
    public abstract void onFire(Player shooter);

    @Override
    public double damage() {
        return properties.getDamage() + bullets.damage + (bullets.damage * random.nextDouble()) - (bullets.damage * random.nextDouble());
    }

    @Override
    public BulletProperties bulletProperties() {
        return bullets;
    }

    private int getRoundsToShoot(Player player) {
        int ammoCount = getAmmo(player);

        int shots = properties.roundsPerShot;

        if (ammoCount < shots) {
            return shots - ammoCount;
        }

        return shots;
    }

    public String getItemName() {
        return gunBaseName;
    }

    public BulletBuilder getBulletBuilder() {
        initBuilder();
        return builder;
    }
}
