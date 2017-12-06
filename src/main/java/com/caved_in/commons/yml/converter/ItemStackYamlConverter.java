package com.caved_in.commons.yml.converter;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.yml.ConfigSection;
import com.caved_in.commons.yml.InternalConverter;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ItemStackYamlConverter implements Converter {
    private InternalConverter converter;

	public ItemStackYamlConverter(InternalConverter converter) {
		this.converter = converter;
	}

	@Override
	public Object toConfig(Class<?> type, Object obj, ParameterizedType genericType) throws Exception {
		org.bukkit.inventory.ItemStack itemStack = (org.bukkit.inventory.ItemStack) obj;

		Map<String, Object> saveMap = new HashMap<>();
		saveMap.put("id", itemStack.getTypeId());

		if (itemStack.getDurability() > 0) {
			saveMap.put("data",itemStack.getDurability());
		}

		saveMap.put("amount", itemStack.getAmount());

		Converter listConverter = converter.getConverter(List.class);

		Map<Enchantment, Integer> itemEnchantments = itemStack.getEnchantments();

		if (itemEnchantments.size() > 0) {
		    Map<String, Object> saveEnchantMap = new HashMap<>();

		    for(Map.Entry<Enchantment, Integer> enchants : itemEnchantments.entrySet()) {
		        saveEnchantMap.put(enchants.getKey().getName(),enchants.getValue());
            }

            saveMap.put("enchantments",converter.getConverter(saveEnchantMap.getClass()).toConfig(saveEnchantMap.getClass(),saveEnchantMap, TypeUtils.parameterize(saveEnchantMap.getClass(),saveEnchantMap.getClass().getGenericInterfaces())));
        }

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
		Map<String, Object> itemstackMap;
		Map<String, Object> metaMap = null;

		if (section == null) {
			throw new NullPointerException("Item configuration section is null");
		}

		if (section instanceof Map) {
			itemstackMap = (Map<String, Object>) section;
			metaMap = (Map<String, Object>) itemstackMap.get("meta");
		} else {
			itemstackMap = (Map<String, Object>)((ConfigSection) section).getRawMap();

			if (itemstackMap.containsKey("meta")) {
				metaMap = (Map<String, Object>)((ConfigSection) itemstackMap.get("meta")).getRawMap();
			}
		}

		int id = (int)itemstackMap.get("id");
		ItemBuilder itemBuilder = ItemBuilder.of(Items.getMaterialById(id));

		if (itemstackMap.containsKey("data")) {
			short data = (short)itemstackMap.get("data");
			itemBuilder.durability(data);
		}

		if (itemstackMap.containsKey("amount")) {
			int amount = (int)itemstackMap.get("amount");
			itemBuilder.amount(amount);
		}

		if (metaMap != null) {
			if (metaMap.containsKey("name")) {
				itemBuilder.name((String)metaMap.get("name"));
			}

			if (metaMap.containsKey("lore")) {
				Converter listConverter = converter.getConverter(List.class);
				List<String> lore = new ArrayList<>();

				lore = (List<String>)listConverter.fromConfig(List.class,metaMap.get("lore"),null);

				itemBuilder.lore(lore);
			}
		}

		/*
		If there's enchantments listed in the yml file for this item, then we're going to parse
		that section of the configuration, and then add it to the item.
		 */
		if (itemstackMap.containsKey("enchantments")) {

		    Map<String, Object> enchantmentMap = new HashMap<>();

		    enchantmentMap = ((ConfigSection)converter.getConverter(enchantmentMap.getClass()).fromConfig(enchantmentMap.getClass(),itemstackMap.get("enchantments"),TypeUtils.parameterize(enchantmentMap.getClass(),enchantmentMap.getClass().getGenericInterfaces()))).getRawMap();

		    for (Map.Entry<String, Object> enchant : enchantmentMap.entrySet()) {
		        Enchantment enchantment = Enchantment.getByName(enchant.getKey());
		        itemBuilder.enchantment(enchantment,(int)enchant.getValue());
            }

		}

		return itemBuilder.item();
	}

	@Override
	public boolean supports(Class<?> type) {
		return ItemStack.class.isAssignableFrom(type);
	}

}
