package com.caved_in.commons.plugin;

public interface CommonPlugin {
	public void startup();

	public void shutdown();

	public String getVersion();

	public String getAuthor();

	public void initConfig();
}
