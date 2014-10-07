package com.caved_in.commons.command.handlers;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.ArgumentHandler;
import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import com.caved_in.commons.item.Enchantments;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentArgumentHandler extends ArgumentHandler<Enchantment> {
	public EnchantmentArgumentHandler() {
	}

	@Override
	public Enchantment transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
		if (!Enchantments.isEnchantment(value)) {
			throw new TransformError(Messages.invalidEnchantment(value));
		}

		return Enchantments.getEnchantment(value);
	}
}
