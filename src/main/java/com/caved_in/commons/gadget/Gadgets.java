package com.caved_in.commons.gadget;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Gadgets {
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
				Commons.debug("Gadget (" + gadget.id() + ") exists as Item " + StringUtil.joinString(Messages.itemInfo(item), "\n"));
				return gadget;
			}
		}
		return null;
	}

	public static Gadget getGadget(int id) {
		return gadgets.get(id);
	}
}
