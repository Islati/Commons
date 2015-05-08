package com.caved_in.commons.command.handlers;

import com.caved_in.commons.command.ArgumentHandler;
import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import org.bukkit.command.CommandSender;
import org.bukkit.material.MaterialData;

public class MaterialDataArgumentHandler extends ArgumentHandler<MaterialData> {

    public MaterialDataArgumentHandler() {
    }

    @Override
    public MaterialData transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
        MaterialData data = null;

        try {
            data = Items.getMaterialDataFromString(value);
        } catch (InvalidMaterialNameException e) {
            throw new TransformError(e.getMessage());
        }

        return data;
    }
}
