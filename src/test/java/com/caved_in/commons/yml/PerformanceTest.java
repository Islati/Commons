package com.caved_in.commons.yml;


import com.caved_in.commons.yml.data.PerformanceConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceTest {
    private static File file;
    private static PerformanceConfig config;


    @BeforeClass
    public static void setup() throws Exception {
        config = new PerformanceConfig();
        file = new File("temp", "performanceConfig.yml");
        config.setConfigFile(file);

        if (file.exists()) {
            FileUtils.forceDelete(file);
        }

        if (!file.getParentFile().exists()) {
            FileUtils.deleteDirectory(file.getParentFile());
        }
    }

    @Test
    public void test1loadPerformance() throws InvalidConfigurationException {
        long start = System.currentTimeMillis();

        assert config != null;
        assert file != null;

        for (int i = 0; i < 100; i++) {
            config.init(file);
        }

        long end = System.currentTimeMillis() - start;

        Assert.assertTrue("" + end, end < 20000);
    }

    @Test
    public void test2savePerformance() throws InvalidConfigurationException {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) config.save(file);

        long end = System.currentTimeMillis() - start;

        Assert.assertTrue("" + end, end < 20000);
    }
}
