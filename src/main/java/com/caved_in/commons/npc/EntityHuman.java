package com.caved_in.commons.npc;

import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.npc.wrappers.DataWatcher;
import com.caved_in.commons.packet.PacketFactory;
import com.caved_in.commons.utilities.CommonUtils;
import com.caved_in.commons.world.Worlds;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EntityHuman implements NPC {
	//UUID Used by NPC's
	public static final UUID NPC_UUID = CommonUtils.uuidFromUnparsedString("0b560de49eb543b981b44edd505fa887");
	private static final double CROUCH_HEIGHT = 1.54;
	private static final double REGULAR_HEIGHT = 1.62;

	private final int id;

	private GameProfile profile;

	private Location location;

	private ItemStack itemInHand;
	private ItemStack helmet;
	private ItemStack body;
	private ItemStack pants;
	private ItemStack shoes;

	private boolean sleeping;
	private boolean crouching;
	private boolean blocking;

	private DataWatcher dataWatcher;

	private static NpcHandler npcHandler = null;

	public EntityHuman(Location location, String name, int id) {
		this.location = location;
		this.id = id;
		this.profile = new GameProfile(NPC_UUID, name);
		this.itemInHand = new ItemStack(Material.AIR);

		this.dataWatcher = new DataWatcher();
		dataWatcher.write(0, (byte) 0);
		dataWatcher.write(1, (short) 0);
		dataWatcher.write(8, (byte) 0);

		this.sleeping = false;
		//Initialize
		init();
	}

	private void init() {
		if (npcHandler == null) {
			npcHandler = NpcHandler.getInstance();
		}
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public String getName() {
		return profile.getName();
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public Location getEyeLocation() {
		Location location = getLocation();
		location.setY(location.getY() + getEyeHeight());
		return location;
	}

	@Override
	public ItemStack getInventory(SlotType type) {
		switch (type) {
			case ITEM_IN_HAND:
				return this.itemInHand;
			case HELMET:
				return this.helmet;
			case BODY_ARMOR:
				return this.body;
			case PANTS:
				return this.pants;
			case SHOES:
				return this.shoes;
			default:
				return null;
		}
	}

	@Override
	public void setName(String name) {
		profile = new GameProfile(NPC_UUID, name);
		npcHandler.updateNPC(this);
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
		npcHandler.updateNPC(this);
	}

	@Override
	public void setInventory(SlotType type, ItemStack item) {
		switch (type) {
			case ITEM_IN_HAND:
				this.itemInHand = item;
				break;
			case HELMET:
				this.helmet = item;
				break;
			case BODY_ARMOR:
				this.body = item;
				break;
			case PANTS:
				this.pants = item;
				break;
			case SHOES:
				this.shoes = item;
				break;
		}
		npcHandler.updateNPC(this);
	}

	@Override
	public void setDataWatcher(DataWatcher dataWatcher) {
		this.dataWatcher = dataWatcher;
		npcHandler.updateNPC(this, PacketFactory.craftMetaDataPacket(this, true));
	}

	@Override
	public DataWatcher getDataWatcher() {
		return this.dataWatcher;
	}

	@Override
	public GameProfile getGameProfile() {
		return profile;
	}

	@Override
	public void hurt() {
		npcHandler.updateNPC(this, PacketFactory.craftHurtPacket(this));
	}

	@Override
	public void swingArm() {
		npcHandler.updateNPC(this, PacketFactory.craftArmSwingPacket(this));
	}

	@Override
	@Deprecated
	public void setBlocking(boolean bool) {
		this.blocking = bool;
		DataWatcher watcher = new DataWatcher();
		watcher.write(0, (byte) (bool ? 16 : 0));
		watcher.write(1, (short) 0);
		watcher.write(8, (byte) 0);
		setDataWatcher(watcher);
	}

	@Override
	public boolean isBlocking() {
		return blocking;
	}

	@Override
	public void setCrouched(boolean bool) {
		this.crouching = bool;
		DataWatcher watcher = new DataWatcher();
		watcher.write(0, (byte) (bool ? 2 : 0));
		watcher.write(1, (short) 0);
		watcher.write(8, (byte) 0);
		setDataWatcher(watcher);
	}

	@Override
	public boolean isCrouching() {
		return crouching;
	}

	@Override
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
		if (this.sleeping) {
			npcHandler.updateNPC(this, PacketFactory.craftSleepPacket(this));
		} else {
			npcHandler.updateNPC(this, PacketFactory.craftLeaveBedPacket(this));
		}
	}

	@Override
	public boolean isSleeping() {
		return sleeping;
	}

	@Override
	public void despawn() {
		npcHandler.updateNPC(this, PacketFactory.craftDestroyPacket(this));
	}

	/**
	 * Need to finish this part.
	 * Code taken from TopCat NPC-Lib.
	 *
	 * @param point
	 */
	@Override
	public void lookAt(Location point) {
		npcHandler.updateNPC(this, PacketFactory.craftHeadRotationPacket(this, location.getYaw()));

		if (getLocation().getWorld().getName().equals(point.getWorld().getName())) {
			final Location npcLoc = getEyeLocation();
			final double xDiff = point.getX() - getLocation().getX();
			final double yDiff = point.getY() - getLocation().getY();
			final double zDiff = point.getZ() - getLocation().getZ();
			final double DistanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
			final double DistanceY = Math.sqrt(DistanceXZ * DistanceXZ + yDiff * yDiff);
			double newYaw = Math.acos(xDiff / DistanceXZ) * 180 / Math.PI;
			final double newPitch = Math.acos(yDiff / DistanceY) * 180 / Math.PI - 90;
			if (zDiff < 0.0) {
				newYaw = newYaw + Math.abs(180 - newYaw) * 2;
			}
			getLocation().setYaw((float) (newYaw - 90));
			getLocation().setPitch((float) newPitch);
			//((EntityPlayer) getEntity()).aP = (float) (newYaw - 90);
		}
	}

	//doesn't work properly
	@Override
	public void walkTo(Location location) {
		this.location = location;
		npcHandler.updateNPC(this, PacketFactory.craftLookMovePacket(this, location));
	}

	@Override
	public double getEyeHeight() {
		return getEyeHeight(false);
	}

	@Override
	public double getEyeHeight(boolean ignoreSneaking) {
		if (isCrouching() && !ignoreSneaking) {
			return CROUCH_HEIGHT;
		} else {
			return REGULAR_HEIGHT;
		}
	}

	@Override
	public void reset() {
		for (SlotType type : SlotType.values()) {
			setInventory(type, new ItemStack(Material.AIR));
		}

		DataWatcher dataWatcher = new DataWatcher();
		dataWatcher.write(0, (byte) 0);
		dataWatcher.write(1, (short) 0);
		dataWatcher.write(8, (byte) 0);

		setDataWatcher(dataWatcher);
	}

	/**
	 * Needs some testing.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
		Object world = Worlds.getHandle(getLocation().getWorld());
		Object entity = null;

		if (Entities.ENTITY_SNOWBALL.isAssignableFrom(projectile)) {
			entity = Entities.invokeProjectile(Entities.ENTITY_SNOWBALL, world);
		} else if (Entities.ENTITY_EGG.isAssignableFrom(projectile)) {
			entity = Entities.invokeProjectile(Entities.ENTITY_EGG, world);
		} else if (Entities.ENTITY_ENDERPEARL.isAssignableFrom(projectile)) {
			entity = Entities.invokeProjectile(Entities.ENTITY_ENDERPEARL, world);
		} else if (Entities.ENTITY_ARROW.isAssignableFrom(projectile)) {
			entity = Entities.invokeProjectile(Entities.ENTITY_ARROW, world);
		} else if (Entities.ENTITY_POTION.isAssignableFrom(projectile)) {
			entity = Entities.invokeProjectile(Entities.ENTITY_POTION, world);
		} else if (Entities.FIREBALL.isAssignableFrom(projectile)) {
			if (Entities.ENTITY_SMALL_FIREBALL.isAssignableFrom(projectile)) {
				entity = Entities.invokeProjectile(Entities.ENTITY_SMALL_FIREBALL, world);
			} else if (Entities.ENTITY_WITHERSKULL.isAssignableFrom(projectile)) {
				entity = Entities.invokeProjectile(Entities.ENTITY_WITHERSKULL, world);
			} else {
				//ENTITY_LARGE_FIREBALL
				entity = Entities.invokeProjectile(Entities.ENTITY_LARGE_FIREBALL, world);
			}
		}

		Location location = getEyeLocation();
//		Vector direction = location.getDirection().multiply(10);
		Entities.setPositionRotation(entity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

		Entities.addEntity(world, entity);
		return Entities.getBukkitEntity(entity);
	}
}
