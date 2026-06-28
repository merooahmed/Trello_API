package com.terrelloAPI.utils.report;

import com.google.common.collect.ImmutableMap;

import com.terrelloAPI.utils.logs.LogsManger;

import java.io.File;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;
import static com.terrelloAPI.utils.dataReader.PropertyReader.getProperty;

public class AllureEnvironmentManager {
    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("Java version:", getProperty("java.runtime.version"))
                        .put("Browser", getProperty("browserType"))
                        .put("Execution Type", getProperty("executionType"))
                        .build(),  String.valueOf(com.terrelloAPI.utils.report.AllureConstants.RESULTS_FOLDER) + File.separator
        );
        com.terrelloAPI.utils.logs.LogsManger.info("Allure environment variables set.");
        com.terrelloAPI.utils.report.AllureBinaryManager.downloadAndExtract();
    }
}