package com.caved_in.commons.properties;

public interface PropertiesBuilder {

	public PropertiesBuilder builder(PropertiesBuilder parent);

	public PropertiesItem parent();
}
