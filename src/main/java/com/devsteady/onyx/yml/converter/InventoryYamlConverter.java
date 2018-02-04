package com.devsteady.onyx.yml.converter;

import com.devsteady.onyx.inventory.Inventories;
import com.devsteady.onyx.yml.ConfigSection;
import com.devsteady.onyx.yml.InternalConverter;
import net.minecraft.util.org.apache.commons.lang3.reflect.TypeUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class InventoryYamlConverter implements Converter {
    private InternalConverter converter;

    public InventoryYamlConverter(InternalConverter converter) {
        this.converter = converter;
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        Inventory inventory = (Inventory)obj;

        Map<String, Object> saveMap = new HashMap<>();

        saveMap.put("name",inventory.getName());
        saveMap.put("rows", Inventories.getRows(inventory.getSize()));

        Map<Integer, Object> contents = new HashMap<>();
        ItemStack[] invItems = inventory.getContents();

        ItemStackYamlConverter itemstackConverter = (ItemStackYamlConverter) converter.getConverter(ItemStack.class);

        for(int i = 0; i < invItems.length; i++) {
            if (invItems[i] == null) {
                continue;
            }
            contents.put(i, itemstackConverter.toConfig(ItemStack.class,invItems[i],null));
        }

        Object mapContents = converter.getConverter(contents.getClass()).toConfig(contents.getClass(),contents,TypeUtils.parameterize(contents.getClass(),contents.getClass().getGenericInterfaces()));

        saveMap.put("contents",contents);

        return saveMap;
    }

    @Override
    public Object fromConfig(Class<?> type, Object section, ParameterizedType parameterizedType) throws Exception {
        Map<String, Object> inventoryData;

        if (section instanceof Map) {
            inventoryData = (Map<String, Object>) section;
        } else {
            inventoryData = (Map<String, Object>) ((ConfigSection) section).getRawMap();
        }

        String name = (String)inventoryData.get("name");
        int rows = (int)inventoryData.get("rows");
        Map<Integer, Object> contents = new HashMap<>();

        Class contentsMapClass = contents.getClass();

        ParameterizedType mapParamType = TypeUtils.parameterize(contents.getClass(),TypeUtils.parameterize(contentsMapClass,contentsMapClass.getGenericInterfaces()));
        contents = (Map<Integer, Object>)converter.getConverter(contents.getClass()).fromConfig(contents.getClass(),inventoryData.get("contents"),mapParamType);

        InventoryYamlConverter invConverter = (InventoryYamlConverter)converter.getConverter(ItemStack.class);
        Inventory inventory = Inventories.makeInventory(name,rows);

        for(Map.Entry<Integer, Object> itemEntry : contents.entrySet()) {
            int slot = itemEntry.getKey();
            ItemStack item = (ItemStack)invConverter.fromConfig(ItemStack.class,itemEntry.getValue(),null);
            inventory.setItem(slot,item);
        }

        return inventory;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.isAssignableFrom(Inventory.class);
    }
}
