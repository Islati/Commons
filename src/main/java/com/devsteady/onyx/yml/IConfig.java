package com.devsteady.onyx.yml;


import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public interface IConfig {
	void save() throws InvalidConfigurationException;

	void save(File file) throws InvalidConfigurationException;

	void init() throws InvalidConfigurationException;

	void init(File file) throws InvalidConfigurationException;

	void reload() throws InvalidConfigurationException;

	void load() throws InvalidConfigurationException;

	void load(File file) throws InvalidConfigurationException;
}
