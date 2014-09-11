package com.caved_in.commons.item;

public interface BuildableItem<T extends BuildableItem> {

	public ItemBuilder itemBuilder();

	public T parent(T parent);
}
