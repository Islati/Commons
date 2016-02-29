package com.caved_in.commons.yml.data;

import com.caved_in.commons.yml.base.Config;

public class ArrayTestConfig extends Config {

    public class InnerConfig extends Config {
        public int x = 7;
    }

    public int[] data = {1, 2, 3};
    public double[][] multiArray = {{1}, {2, 3}, {4}};
    public InnerConfig[] configs = {new InnerConfig(),
            new InnerConfig(),
            new InnerConfig()
    };
    public String[] strings = {"Hello", "World", "!"};
}
