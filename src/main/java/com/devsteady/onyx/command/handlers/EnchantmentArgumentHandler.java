package com.devsteady.onyx.command.handlers;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.command.ArgumentHandler;
import com.devsteady.onyx.command.CommandArgument;
import com.devsteady.onyx.command.TransformError;
import com.devsteady.onyx.item.Enchantments;
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
