package com.caved_in.commons.yml;

import com.caved_in.commons.yml.data.Messages;
import org.junit.Assert;
import org.junit.Test;

public class SerializeSingletonTest {
    @Test
    public void init() {
        String message = Messages.get("general");
        Assert.assertEquals(message, "Did I do that!");
    }

    @Test
    public void reinit() {
        Messages.instance = null;
        init();
        init();
    }
}
