package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.BaseTest;
import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.ObjectConverter;
import com.caved_in.commons.yml.data.SimpleObjectConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SimpleConverterTest extends BaseTest {
    public void setup() throws Exception {
        config = new SimpleObjectConfig();
        config.getYamlConfigurationSettings().addConverter(ObjectConverter.class);
        filename = "simpleConverterTest.yml";

        before();
    }

    @Test
    public void onInit() throws Exception {
        config.init(file);

        String fileContents = Util.readFile(file);

        assertThat(fileContents.equals(
                "TestMap:\n" +
                        "  test: test\n" +
                        "TestSet:\n" +
                        "- test\n"));
    }
}
