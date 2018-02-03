package com.devsteady.onyx.command.handlers;

import com.devsteady.onyx.command.ArgumentHandler;
import com.devsteady.onyx.command.CommandArgument;
import com.devsteady.onyx.command.TransformError;
import com.devsteady.onyx.exceptions.InvalidMaterialNameException;
import com.devsteady.onyx.item.Items;
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
