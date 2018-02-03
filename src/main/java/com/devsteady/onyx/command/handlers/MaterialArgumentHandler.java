package com.devsteady.onyx.command.handlers;

import com.devsteady.onyx.command.ArgumentHandler;
import com.devsteady.onyx.command.CommandArgument;
import com.devsteady.onyx.command.TransformError;
import com.devsteady.onyx.exceptions.InvalidMaterialNameException;
import com.devsteady.onyx.item.Items;
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

        if (StringUtils.isNumeric(value)) {
            material = Items.getMaterialById(Integer.parseInt(value));
        } else {
            try {
                material = Items.getMaterialByName(value);
            } catch (InvalidMaterialNameException ignored) {

            }
        }

        if (material == null) {
            throw new TransformError(argument.getMessage("parse_error"));
        }

        return material;
    }
}
