package com.terrelloAPI.utils.report;


import com.terrelloAPI.utils.OSUtils;
import com.terrelloAPI.utils.TerminalUtils;
import com.terrelloAPI.utils.TimeManger;
import com.terrelloAPI.utils.logs.LogsManger;
import com.terrelloAPI.utils.report.AllureBinaryManager;
import com.terrelloAPI.utils.report.AllureConstants;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import static com.terrelloAPI.utils.dataReader.PropertyReader.getProperty;
import static com.terrelloAPI.utils.report.AllureConstants.HISTORY_FOLDER;
import static com.terrelloAPI.utils.report.AllureConstants.RESULTS_HISTORY_FOLDER;

public class AllureReportGenerator {
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstants.REPORT_PATH : AllureConstants.FULL_REPORT_PATH;
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstants.RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        com.terrelloAPI.utils.TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }


    public static String renameReport() {
        String newFileName = AllureConstants.REPORT_PREFIX + com.terrelloAPI.utils.TimeManger.getTimestamp() + AllureConstants.REPORT_EXTENSION;
        com.terrelloAPI.FileUtils.renameFile(AllureConstants.REPORT_PATH.resolve(AllureConstants.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }





    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local") )return;

        Path reportPath = AllureConstants.REPORT_PATH.resolve(reportFileName);
        switch (com.terrelloAPI.utils.OSUtils.getOS()) {
            case WINDOWS -> com.terrelloAPI.utils.TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportPath.toString());
            case MAC, LINUX -> com.terrelloAPI.utils.TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default -> com.terrelloAPI.utils.logs.LogsManger.warn("Opening Allure Report is not supported on this OS.");
        }
    }
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(HISTORY_FOLDER.toFile(), RESULTS_HISTORY_FOLDER.toFile());
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error copying history files", e.getMessage());
        }
    }



}
