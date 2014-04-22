package com.caved_in.commons.menu;

public enum PageDisplay {
	DEFAULT("<name> (Page <page> of <maxpage>)"),
	SHORTHAND("<name> (P.<page>/<maxpage>)");

	private String formatting;

	private PageDisplay(String formatting) {
		this.formatting = formatting;
	}

	@Override
	public String toString() {
		return this.formatting;
	}
}
