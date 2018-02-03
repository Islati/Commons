package com.devsteady.onyx.inventory.menu;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MenuAction {
    CLOSE(MenuCloseBehaviour.class),
    OPEN(MenuOpenBehaviour.class);

    private static final Map<Class, MenuAction> menuTypes = new HashMap<>();

    static {
        for (MenuAction menuAction : EnumSet.allOf(MenuAction.class)) {
            menuTypes.put(menuAction.getBehaviourClass(), menuAction);
        }
    }

    private Class clazz;

    MenuAction(Class clazz) {
        this.clazz = clazz;
    }

    public Class getBehaviourClass() {
        return clazz;
    }

    public static boolean isType(MenuBehaviour behaviour, MenuAction type) {
        return type.getBehaviourClass().isInstance(behaviour);
    }

    public static MenuAction getType(MenuBehaviour behaviour) {
        for (Map.Entry<Class, MenuAction> entry : menuTypes.entrySet()) {
            if (entry.getValue().getBehaviourClass().isInstance(behaviour)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
