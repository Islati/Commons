package com.caved_in.commons.config.serializers.json;

import com.caved_in.commons.world.Worlds;
import com.google.gson.*;
import org.bukkit.Location;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.lang.reflect.Type;

public class LocationJsonSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        return JsonBuilderFactory.buildObject()
                .add("x", location.getX())
                .add("y", location.getY())
                .add("z", location.getZ())
                .add("pitch", location.getPitch())
                .add("yaw", location.getYaw())
                .add("world", location.getWorld().getName())
                .getJson();
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject locObj = jsonElement.getAsJsonObject();
        return new Location(
                Worlds.getWorld(locObj.get("world").getAsString()),
                locObj.get("x").getAsDouble(),
                locObj.get("y").getAsDouble(),
                locObj.get("z").getAsDouble(),
                locObj.get("yaw").getAsFloat(),
                locObj.get("pitch").getAsFloat()
        );
    }
}
