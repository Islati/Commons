package com.caved_in.commons.reflection;

import com.caved_in.commons.chat.Chat;
import org.bukkit.Bukkit;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtilities {
	private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();


	public static final String NMS_PATH = getNMSPackageName();
	public static final String CB_PATH = getCBPackageName();

	public static String getNMSPackageName() {
		return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	public static String getCBPackageName() {
		return "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}

	public static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1) + ".";
		return version;
	}

	/**
	 * Retrieve a class.
	 *
	 * @param name name of the class to retrieve
	 * @return the class if found, otherwise null.
	 */
	public static Class getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			Chat.debug("Could not find class: " + name + "!");
		}
		return null;
	}

	/**
	 * Retrieve a(n) Craft Bukkit class
	 *
	 * @param className name of the class to retrieve
	 * @return the class if found, otherwise null
	 */
	public static Class getCBClass(String className) {
		return getClass(CB_PATH + "." + className);
	}

	public static Class<?> getNMSClass(String className) {
		String fullName = "net.minecraft.server." + getVersion() + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Class<?> getOBCClass(String className) {
		String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Class<?> getOBCClass(String className, String subPackage) {
		String fullName = "org.bukkit.craftbukkit." + getVersion() + subPackage + "." + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieve a field by its name.
	 * If the field is by default inaccessible then change it to allow access.
	 *
	 * @param clazz     class to retrieve the field from
	 * @param fieldName name of the field to retrieve.
	 * @return the field if found, otherwise null.
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			setAccessible(field);
			return field;
		} catch (Exception e) {
			Chat.debug("No such field: " + fieldName + "!");
		}
		return null;
	}

	public static <T> T getField(Class<?> clazz, String fieldName, Object instance) {
		try {
			return (T) getField(clazz, fieldName).get(instance);
		} catch (IllegalAccessException e) {
			Chat.debug("Failed to access field: " + fieldName + "!");
		}
		return null;
	}

	public static void setField(Class<?> clazz, String fieldName, Object instance, Object value) {
		try {
			getField(clazz, fieldName).set(instance, value);
		} catch (IllegalAccessException e) {
			Chat.debug("Could not set new field value for: " + fieldName);
		}
	}


	private Class<?> getPrimitiveType(Class<?> clazz) {
		return CORRESPONDING_TYPES.containsKey(clazz) ? CORRESPONDING_TYPES
				.get(clazz) : clazz;
	}

	private Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
		int a = classes != null ? classes.length : 0;
		Class<?>[] types = new Class<?>[a];
		for (int i = 0; i < a; i++) {
			types[i] = getPrimitiveType(classes[i]);
		}
		return types;
	}

	/**
	 * Method stuff
	 */

	public static Method getMethod(Class<?> clazz, String methodName) {
		try {

			Method method = clazz.getMethod(methodName);

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			return method;
		} catch (NoSuchMethodException e) {
			Chat.debug("No such method: " + methodName + " on class" + clazz.getCanonicalName() + "!");
		}
		return null;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
		try {

			Method method = clazz.getDeclaredMethod(methodName, params);

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			return method;
		} catch (NoSuchMethodException e) {
			Chat.debug("No such method: " + methodName + " on class" + clazz.getCanonicalName() + "!");
		}
		return null;
	}

	public static <T> T invokeMethod(Method method, Object instance, Object... args) {
		try {
			return (T) method.invoke(instance, args);
		} catch (IllegalAccessException e) {
			Chat.debug("Failed to access method: " + method.getName() + "!");
		} catch (InvocationTargetException e) {
			Chat.debug("Failed to invoke method: " + method.getName() + "!");
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
			Chat.debug("No such constructor!");
		}
		return null;
	}

	public static <T> T invokeConstructor(Constructor constructor, Object... args) {
		try {
			return (T) constructor.newInstance(args);
		} catch (InstantiationException e) {
			Chat.debug("Failed to instantiate constructor: " + constructor.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Chat.debug("Failed to access constructor: " + constructor.getName());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Chat.debug("Failed to invoke constructor: " + constructor.getName());
			e.printStackTrace();
		}
		return null;
	}

	public static void setValue(Object instance, String fieldName, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		setAccessible(field).set(instance, value);
	}

	public static void setValue(Class<?> clazz, String field, Object value) throws Exception {
		Field f = getField(clazz, field);
		setAccessible(f).set(null, value);
	}

	public static void setValue(Field field, Object instance, Object value) throws Exception {
		setAccessible(field).set(instance, value);
	}

	public static Object getValue(Object instance, String fieldName) throws Exception {
		Field field = instance.getClass().getDeclaredField(fieldName);
		setAccessible(field);
		return field.get(instance);
	}

	public static Object getValue(Class<?> clazz, String fieldName, Object object) throws Exception {
		return (getValue(getField(clazz, fieldName), object));
	}

	public static Object getValue(Field field, Object object) throws Exception {
		return field.get(object);
	}


	public static void removeFinal(Field field) throws Exception {
		setAccessible(field);

		if (!isFinal(field)) {
			Chat.debug("Field " + field.getName() + " in class " + field.getClass().getCanonicalName() + " is not final.. Cannot remove final");
			return;
		}

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}

	public static boolean hasField(Class<?> clazz, String field) {
		try {
			Field f = clazz.getField(field);
		} catch (NoSuchFieldException e) {
			return false;
		}
		return true;
	}

	public static boolean hasDeclaredField(Class<?> clazz, String field) {
		try {
			Field f = clazz.getDeclaredField(field);
		} catch (NoSuchFieldException e) {
			return false;
		}

		return true;
	}

	public static boolean isFinal(Field field) throws Exception {
		return Modifier.isFinal(field.getModifiers());
	}

	public static boolean isFinal(Class<?> clazz, String field) throws Exception {
		Field f = getField(clazz, field);
		return isFinal(f);
	}

	public static boolean isStatic(Class<?> clazz, String field) throws Exception {
		Field f = getField(clazz, field);
		return isStatic(f);
	}

	public static boolean isStatic(Field field) throws Exception {
		return Modifier.isStatic(field.getModifiers());
	}

	public static boolean isModifieable(Field field) throws Exception {
		return !isFinal(field) && isAccessible(field);
	}

	public static boolean isAccessible(Field field) throws Exception {
		return field.isAccessible();
	}

	public static Field setAccessible(Field f) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
		return f;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotation(Class<T> clazz, Method method, int parameterIndex) {
		for (Annotation annotation : method.getParameterAnnotations()[parameterIndex]) {
			if (annotation.annotationType() == clazz) {
				return (T) annotation;
			}
		}
		return null;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Enum<?> getEnum(String enumFullName) {
		String[] x = enumFullName.split("\\.(?=[^\\.]+$)");
		if (x.length == 2) {
			String enumClassName = x[0];
			String enumName = x[1];
			try {
				Class<Enum> cl = (Class<Enum>) Class.forName(enumClassName);
				return Enum.valueOf(cl, enumName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}