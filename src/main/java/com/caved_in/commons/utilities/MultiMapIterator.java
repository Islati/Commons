package com.caved_in.commons.utilities;

import java.util.Iterator;

public class MultiMapIterator<A, B, C> implements Iterator<A> {

	private MultiMap<A, B, C> mapBase = null;
	private A currentlyObject = null;
	private int index = 0, lastIndex;

	public MultiMapIterator(MultiMap<A, B, C> mapBase) {
		this.mapBase = mapBase.clone();
	}

	@Override
	public boolean hasNext() {
		return (index < mapBase.size());
	}

	@Override
	public A next() {
		A object = mapBase.getKey(index);
		currentlyObject = object;

		lastIndex = index;
		index++;

		return object;
	}

	public A getCurrently() {
		return currentlyObject;
	}

	public MultiMapResource<A, B, C> getResource() {
		return this.mapBase.getResource(currentlyObject);
	}

	@Override
	public void remove() {
		mapBase.remove(lastIndex);
	}

}