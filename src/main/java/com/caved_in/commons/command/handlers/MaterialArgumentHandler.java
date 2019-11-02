package com.caved_in.commons.command.handlers;

import com.caved_in.commons.command.ArgumentHandler;
import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;


public class MaterialArgumentHandler extends ArgumentHandler<Material> {

    public MaterialArgumentHandler() {
        setMessage("parse_error", "The parameter [%p] is not a valid material.");
        setMessage("include_error", "There is no material named %1");
        setMessage("exclude_error", "There is no material named %1");
    }

    @Override
    public Material transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
        Material material = null;

        try {
            material = Items.getMaterialByName(value);
        } catch (InvalidMaterialNameException ignored) {
            throw new TransformError(argument.getMessage("parse_error"));
        }

        if (material == null) {
            throw new TransformError(argument.getMessage("parse_error"));
        }

        return material;
    }
}
