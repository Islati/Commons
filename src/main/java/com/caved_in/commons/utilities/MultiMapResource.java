package com.caved_in.commons.utilities;

public class MultiMapResource<A, B, C> {

	private A key = null;
	private B firstValue = null;
	private C secondValue = null;

	private int index;

	public MultiMapResource(A key, MultiMap<A, B, C> map) {
		this.key = key;
		this.firstValue = map.getFirstValue(key);
		this.secondValue = map.getSecondValue(key);

		this.index = map.getIndex(key);
	}


	public A getKey() {
		return key;
	}

	public B getFirstValue() {
		return firstValue;
	}

	public C getSecondValue() {
		return secondValue;
	}

	public int getIndex() {
		return index;
	}

}