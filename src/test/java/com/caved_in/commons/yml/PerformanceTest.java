package com.caved_in.commons.yml;


import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.data.PerformanceConfig;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerformanceTest extends BaseTest {

    public void setup() throws Exception {
        config = new PerformanceConfig();
        filename = "performanceConfig.yml";
    }

    @Test
    public void test1loadPerformance() throws InvalidConfigurationException {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) config.init(file);

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
