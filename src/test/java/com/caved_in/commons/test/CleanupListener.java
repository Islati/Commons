package com.caved_in.commons.test;

import org.apache.commons.io.FileUtils;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.io.File;

public class CleanupListener extends RunListener {
    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);

        FileUtils.cleanDirectory(new File("temp"));
        FileUtils.deleteDirectory(new File("temp"));
    }
}
