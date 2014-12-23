package com.caved_in.commons.reflection;

import com.caved_in.commons.chat.Chat;

import java.lang.reflect.Field;

public class SafeField<T> {

	private Field field;

	public SafeField(Field field) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		this.field = field;
	}

	public SafeField(Class<?> clazz, String fieldName) {
		this.field = ReflectionUtilities.getField(clazz, fieldName);
	}

	public T get(Object instance) {
		try {
			return (T) field.get(instance);
		} catch (IllegalAccessException e) {
			Chat.messageAll("Could not access field: " + toString());
			return null;
		}
	}

	public void set(Object instance, Object value) {
		try {
			this.field.set(instance, value);
		} catch (IllegalAccessException e) {
			Chat.messageAll("Could not access field: " + toString());
		}
	}
}
