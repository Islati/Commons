package com.caved_in.commons.npc;

import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import com.caved_in.commons.npc.wrappers.DataWatcher;

public interface NPC {

    public int getEntityID();

    public String getName();

    public Location getLocation();

    public Location getEyeLocation();

    public ItemStack getInventory(SlotType type);

    public void setName(String name);

    public void setLocation(Location location);

    public void setInventory(SlotType type, ItemStack item);

    public void setDataWatcher(DataWatcher dataWatcher);

    public DataWatcher getDataWatcher();

    public GameProfile getGameProfile();

    public void hurt();

    public void swingArm();

    public void setBlocking(boolean blocking);

    public boolean isBlocking();

    public void setSleeping(boolean sleeping);

    public boolean isSleeping();

    public void setCrouched(boolean crouched);

    public boolean isCrouching();

    public void despawn();

    public void lookAt(Location location);

    public void walkTo(Location location);

    public double getEyeHeight();

    public double getEyeHeight(boolean ignoreSneaking);

    public void reset();

    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile);

	public String getText();
	
	public void setText(String text);

}
