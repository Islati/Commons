package com.caved_in.commons.reflection;

import com.caved_in.commons.Commons;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtilities {
	public static final String NMS_PATH = getNMSPackageName();
	public static final String CB_PATH = getCBPackageName();

	public static String getNMSPackageName() {
		return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	public static String getCBPackageName() {
		return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	/**
	 * Class stuff
	 */

	public static Class getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			Commons.debug("Could not find class: " + name + "!");
		}
		return null;
	}

	public static Class getNMSClass(String className) {
		return getClass(NMS_PATH + "." + className);
	}

	public static Class getCBClass(String className) {
		return getClass(CB_PATH + "." + className);
	}

	/**
	 * Field stuff
	 */

	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);

			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			return field;
		} catch (NoSuchFieldException e) {
			Commons.messageConsole("No such field: " + fieldName + "!");
		}
		return null;
	}

	public static <T> T getField(Class<?> clazz, String fieldName, Object instance) {
		try {
			return (T) getField(clazz, fieldName).get(instance);
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Failed to access field: " + fieldName + "!");
		}
		return null;
	}

	public static void setField(Class<?> clazz, String fieldName, Object instance, Object value) {
		try {
			getField(clazz, fieldName).set(instance, value);
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Could not set new field value for: " + fieldName);
		}
	}

	/**
	 * Method stuff
	 */

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
		try {
			Method method = clazz.getDeclaredMethod(methodName, params);

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			return method;
		} catch (NoSuchMethodException e) {
			Commons.messageConsole("No such method: " + methodName + "!");
		}
		return null;
	}

	public static <T> T invokeMethod(Method method, Object instance, Object... args) {
		try {
			return (T) method.invoke(instance, args);
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Failed to access method: " + method.getName() + "!");
		} catch (InvocationTargetException e) {
			Commons.messageConsole("Failed to invoke method: " + method.getName() + "!");
		}
		return null;
	}

	public static Constructor getConstructor(Class<?> clazz, Class<?>... params) {
		try {
			Constructor constructor = clazz.getConstructor(params);

			if (!constructor.isAccessible()) {
				constructor.setAccessible(true);
			}

			return constructor;
		} catch (NoSuchMethodException e) {
			Commons.messageConsole("No such constructor!");
		}
		return null;
	}

	public static <T> T invokeConstructor(Constructor constructor, Object... args) {
		try {
			return (T) constructor.newInstance(args);
		} catch (InstantiationException e) {
			Commons.messageConsole("Failed to instantiate constructor: " + constructor.getName());
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Failed to access constructor: " + constructor.getName());
		} catch (InvocationTargetException e) {
			Commons.messageConsole("Failed to invoke constructor: " + constructor.getName());
		}
		return null;
	}

	public static void setValue(Object instance, String fieldName, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	public static Object getValue(Object instance, String fieldName) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}
}