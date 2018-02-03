package com.devsteady.onyx.yml.converter;


import com.devsteady.onyx.yml.ConfigSection;
import com.devsteady.onyx.yml.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public class UuidYamlConverter implements Converter {
	private InternalConverter internalConverter;

	public UuidYamlConverter(InternalConverter internalConverter) {
		this.internalConverter = internalConverter;
	}

	@Override
	public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) {
		UUID id = (UUID)obj;
		Map<String, Object> saveMap = new HashMap<>();
		saveMap.put("uuid",id.toString());
		return saveMap;
	}

	@Override
	public Object fromConfig(Class type, Object section, ParameterizedType genericType) {
		Map<String, Object> loadMap = new HashMap<>();

		if (section instanceof Map) {
			loadMap = (Map<String, Object>)section;
		} else {
			loadMap = (Map<String, Object>)((ConfigSection)section).getRawMap();
		}

		return UUID.fromString((String) loadMap.get("uuid"));
	}

	@Override
	public boolean supports(Class<?> type) {
		return type.isArray();
	}
}
