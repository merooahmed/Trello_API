package com.terrelloAPI.utils.report;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.terrelloAPI.utils.dataReader.PropertyReader.getProperty;

public class AllureConstants {
    public static final Path USER_DIR = Paths.get(getProperty("user.dir"));
    public static final Path USER_HOME = Paths.get(getProperty("user.home"));

    public static final Path RESULTS_FOLDER = Paths.get(String.valueOf(USER_DIR), "test-output", "allure-results");
    public static final Path REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-output", "reports");
    public static final Path FULL_REPORT_PATH = Paths.get(String.valueOf(USER_DIR), "test-output", "full-report");

    public static final Path HISTORY_FOLDER = Paths.get(FULL_REPORT_PATH.toString(), "history");
    public static final Path RESULTS_HISTORY_FOLDER = Paths.get(RESULTS_FOLDER.toString(), "history");

    public static final String INDEX_HTML = "index.html";
    public static final String REPORT_PREFIX = "AllureReport_";
    public static final String REPORT_EXTENSION = ".html";

    public static final String ALLURE_ZIP_BASE_URL = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    public static final Path EXTRACTION_DIR = Paths.get(String.valueOf(USER_HOME), ".m2/repository/allure");
}
