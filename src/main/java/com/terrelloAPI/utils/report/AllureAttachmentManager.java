package com.terrelloAPI.utils.report;



import com.terrelloAPI.utils.logs.LogsManger;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.terrelloAPI.utils.dataReader.PropertyReader.getProperty;


public class AllureAttachmentManager {

   /* public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                com.terrelloAPI.utils.logs.LogsManger.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error attaching screenshot", e.getMessage());
        }
    }*/

    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(com.terrelloAPI.utils.logs.LogsManger.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error attaching logs", e.getMessage());
        }
    }

    /*public static void attachRecords(String testMethodName) {
        if (getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                File record = new File(ScreenRecordManger.RECORDING_PATH+testMethodName);
                if (record != null && record.getName().endsWith(".mp4")) {
                    Allure.addAttachment(testMethodName, "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                }
            } catch (Exception e) {
                com.terrelloAPI.utils.logs.LogsManger.error("Error attaching records", e.getMessage());
            }
        }
    }*/
}