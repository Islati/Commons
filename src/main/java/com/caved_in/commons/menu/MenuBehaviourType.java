package com.caved_in.commons.menu;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MenuBehaviourType {
	CLOSE(MenuCloseBehaviour.class),
	OPEN(MenuOpenBehaviour.class);

	private static final Map<Class, MenuBehaviourType> menuTypes = new HashMap<>();

	static {
		for (MenuBehaviourType menuBehaviourType : EnumSet.allOf(MenuBehaviourType.class)) {
			menuTypes.put(menuBehaviourType.getBehaviourClass(), menuBehaviourType);
		}
	}

	private Class clazz;

	MenuBehaviourType(Class clazz) {
		this.clazz = clazz;
	}

	public Class getBehaviourClass() {
		return clazz;
	}

	public static boolean isType(MenuBehaviour behaviour, MenuBehaviourType type) {
		return type.getBehaviourClass().isInstance(behaviour);
	}

	public static MenuBehaviourType getType(MenuBehaviour behaviour) {
		for (Map.Entry<Class, MenuBehaviourType> entry : menuTypes.entrySet()) {
			if (entry.getValue().getBehaviourClass().isInstance(behaviour)) {
				return entry.getValue();
			}
		}
		return null;
	}
}
