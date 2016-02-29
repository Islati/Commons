package com.caved_in.commons.yml.base;

import org.junit.Before;

import java.io.File;

public abstract class BaseTest {
    protected String filename;
    protected Config config;
    protected File file;

    @Before
    public void before() throws Exception {
        setup();

        file = new File("temp", filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    public abstract void setup() throws Exception;
}
