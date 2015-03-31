package com.caved_in.commons.game.gadget;

import com.caved_in.commons.game.guns.BaseGun;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Gadgets {
	//Used to assign gadgets an ID auto-magically
//	private static final AtomicInteger ids = new AtomicInteger(1);
	private static final Random random = new Random();

	//todo Implement methods to get first free int for gadget registration
	private static final Map<Integer, Gadget> gadgets = new HashMap<>();

	//todo implement int method to return the registered gadgets id
	public static void registerGadget(Gadget gadget) {
		gadgets.put(gadget.id(), gadget);
	}

	public static boolean isGadget(ItemStack item) {
		return getGadget(item) != null;
	}

	public static boolean isGadget(int id) {
		return gadgets.containsKey(id);
	}

	public static Gadget getGadget(ItemStack item) {
		for (Gadget gadget : gadgets.values()) {
			if (gadget instanceof BaseGun) {

				BaseGun gun = (BaseGun) gadget;
				if (Items.nameContains(item, gun.getItemName())) {
					return gun;
				}
			} else if (gadget.getItem().isSimilar(item)) {
				return gadget;
			}
		}
		return null;
	}

	public static Gadget getGadget(int id) {
		return gadgets.get(id);
	}

	public static void spawnGadget(Gadget gadget, Location location) {
		Worlds.dropItem(location, gadget.getItem());
	}

	public static Gadget getRandomGadget() {
		List<Gadget> gadgetList = Lists.newArrayList(gadgets.values());
		return gadgetList.get(random.nextInt(gadgetList.size()));
	}
}
