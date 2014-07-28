package com.caved_in.commons.plugin.game.gadget;

import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Gadgets {
	private static final Random random = new Random();

	private static final Map<Integer, Gadget> gadgets = new HashMap<>();

	public static void registerGadget(Gadget gadget) {
		gadgets.put(gadget.id(), gadget);
	}

	public static boolean isGadget(ItemStack item) {
		return getGadget(item) != null;
	}

	public static Gadget getGadget(ItemStack item) {
		for (Gadget gadget : gadgets.values()) {
			if (gadget.getItem().isSimilar(item)) {
//				Commons.debug("Gadget (" + gadget.id() + ") exists as Item " + StringUtil.joinString(Messages.itemInfo(item), "\n"));
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
