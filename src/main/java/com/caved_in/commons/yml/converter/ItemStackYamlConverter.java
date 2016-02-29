package com.caved_in.commons.yml.converter;

import com.caved_in.commons.yml.ConfigSection;
import com.caved_in.commons.yml.InternalConverter;
import org.bukkit.Material;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ItemStackYamlConverter implements YamlConverter {
	private InternalConverter converter;

	public ItemStackYamlConverter(InternalConverter converter) {
		this.converter = converter;
	}

	@Override
	public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
		org.bukkit.inventory.ItemStack itemStack = (org.bukkit.inventory.ItemStack) obj;

		Map<String, Object> saveMap = new HashMap<>();
		saveMap.put("id", itemStack.getType() + ((itemStack.getDurability() > 0) ? ":" + itemStack.getDurability() : ""));
		saveMap.put("amount", itemStack.getAmount());

		YamlConverter listConverter = converter.getConverter(List.class);

		//todo implement enchant serialize

		Map<String, Object> meta = null;
		if (itemStack.hasItemMeta()) {
			meta = new HashMap<>();
			meta.put("name", itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : null);
			meta.put("lore", itemStack.getItemMeta().hasLore() ? listConverter.toConfig(List.class, itemStack.getItemMeta().getLore(), null) : null);
		}

		saveMap.put("meta", meta);

		return saveMap;
	}

	@Override
	public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
		Map itemstackMap;
		Map metaMap = null;

		if (section instanceof Map) {
			itemstackMap = (Map) section;
			metaMap = (Map) itemstackMap.get("meta");
		} else {
			itemstackMap = ((ConfigSection) section).getRawMap();
            if (itemstackMap.get("meta") != null) {
                metaMap = ((ConfigSection) itemstackMap.get("meta")).getRawMap();
            }
		}

		String[] temp = ((String) itemstackMap.get("id")).split(":");
		org.bukkit.inventory.ItemStack itemStack = (org.bukkit.inventory.ItemStack)
				type.getConstructor(Material.class).newInstance(Material.valueOf(temp[0]));
		itemStack.setAmount((int) itemstackMap.get("amount"));

		if (temp.length == 2) {
			itemStack.setDurability(Short.valueOf(temp[1]));
		}

		if (metaMap != null) {
			if (metaMap.get("name") != null) {
				itemStack.getItemMeta().setDisplayName((String) metaMap.get("name"));
			}

			if (metaMap.get("lore") != null) {
				YamlConverter listConverter = converter.getConverter(List.class);
				itemStack.getItemMeta().setLore((List<String>) listConverter.fromConfig(List.class, metaMap.get("lore"), null));
			}
		}

		return itemStack;
	}

	@Override
	public boolean supports(Class<?> type) {
		return org.bukkit.inventory.ItemStack.class.isAssignableFrom(type);
	}

}
