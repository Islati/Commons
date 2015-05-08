package com.caved_in.commons.command.handlers;

import com.caved_in.commons.command.ArgumentHandler;
import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import com.caved_in.commons.entity.Entities;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;


public class EntityTypeArgumentHandler extends ArgumentHandler<EntityType> {
    public EntityTypeArgumentHandler() {
        setMessage("parse_error", "There is no entity named %1");
        setMessage("include_error", "There is no entity named %1");
        setMessage("exclude_error", "There is no entity named %1");
    }

    @Override
    public EntityType transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
        EntityType type = null;

        if (StringUtils.isNumeric(value)) {
            type = EntityType.fromId(Integer.parseInt(value));
        } else {
            type = Entities.getTypeByName(value);
        }

        if (type == null) {
            throw new TransformError(argument.getMessage("parse_error", value));
        }

        return type;

    }


}
