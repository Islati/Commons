package com.caved_in.commons.yml.converter;

import com.caved_in.commons.yml.ConfigSection;
import com.caved_in.commons.yml.InternalConverter;
import org.bukkit.Bukkit;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class LocationYamlConverter implements Converter {
	public LocationYamlConverter(InternalConverter converter) {
	}

	@Override
	public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
		Map<String, Object> saveMap = new HashMap<>();
		if (obj == null) {
			saveMap.put("world","null");
			saveMap.put("x",0);
			saveMap.put("y",0);
			saveMap.put("z",0);
			saveMap.put("yaw",0);
			saveMap.put("pitch",0);
			return saveMap;
		}

		org.bukkit.Location location = (org.bukkit.Location) obj;
		saveMap.put("world", location.getWorld().getName());
		saveMap.put("x", location.getX());
		saveMap.put("y", location.getY());
		saveMap.put("z", location.getZ());
		saveMap.put("yaw", location.getYaw());
		saveMap.put("pitch", location.getPitch());

		return saveMap;
	}

	@Override
	public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
		Map<String, Object> locationMap;
		if (section instanceof Map) {
			locationMap = (Map<String, Object>) section;
		} else {
			try {
				locationMap = (Map<String, Object>) ((ConfigSection) section).getRawMap();
			} catch (NullPointerException ex) {
				return null;
			}
		}

		if (locationMap.get("world").equals("null")) {
			return null;
		}

		Float yaw;
		if (locationMap.get("yaw") instanceof Double) {
			Double dYaw = (Double) locationMap.get("yaw");
			yaw = dYaw.floatValue();
		} else {
			yaw = (Float) locationMap.get("yaw");
		}

		Float pitch;
		if (locationMap.get("pitch") instanceof Double) {
			Double dPitch = (Double) locationMap.get("pitch");
			pitch = dPitch.floatValue();
		} else {
			pitch = (Float) locationMap.get("pitch");
		}

		return new org.bukkit.Location(Bukkit.getWorld((String) locationMap.get("world")),
				(Double) locationMap.get("x"),
				(Double) locationMap.get("y"),
				(Double) locationMap.get("z"),
				yaw,
				pitch);
	}

	@Override
	public boolean supports(Class<?> type) {
		return org.bukkit.Location.class.isAssignableFrom(type);
	}

}
