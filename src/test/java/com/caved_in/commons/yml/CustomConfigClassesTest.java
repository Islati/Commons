package com.caved_in.commons.yml;

import com.caved_in.commons.yml.base.Util;
import com.caved_in.commons.yml.data.CustomConfigClasses;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomConfigClassesTest {
    private CustomConfigClasses customConfigClasses;
    private File file;

    @Before
    public void before() {
        customConfigClasses = new CustomConfigClasses();

        file = new File("temp", "customConfigClasses.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void test1initNull() throws InvalidConfigurationException, IOException {
        customConfigClasses.init(file);

        String[] fileLines = Util.readFileSplit(file);
        String fileContents = Util.readFile(file, true);

        assertThat(fileContents, containsString("# This is a example Config showing you how to use Custom Classes inside the Config"));
        assertThat(fileContents, containsString("yaw: null"));
        assertArrayEquals(fileLines, new String[]{"# This is a example Config showing you how to use Custom Classes inside the Config",
                "",
                "Spawn:",
                "  # To configure the default Spawn Location please use '/plugin setspawn' ingame.",
                "  # If you modify it here it can not work.",
                "  Location:",
                "    server: null",
                "    world: null",
                "    x: null",
                "    y: null",
                "    z: null",
                "    pitch: null",
                "    yaw: null"});
    }
}
