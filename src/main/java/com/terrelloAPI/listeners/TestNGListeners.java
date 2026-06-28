package com.terrelloAPI.listeners;

import com.terrelloAPI.FileUtils;
import com.terrelloAPI.utils.logs.LogsManger;
import com.terrelloAPI.utils.report.AllureAttachmentManager;
import com.terrelloAPI.utils.report.AllureConstants;
import com.terrelloAPI.utils.report.AllureEnvironmentManager;
import com.terrelloAPI.utils.report.AllureReportGenerator;
import org.aspectj.util.FileUtil;

import org.testng.IExecutionListener;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

import static com.terrelloAPI.utils.dataReader.PropertyReader.loadProperties;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {
    @Override
    public void onExecutionStart() {
        com.terrelloAPI.utils.logs.LogsManger.info("Test Execution started");


        cleanTestOutputDirectories();
        com.terrelloAPI.utils.logs.LogsManger.info("Directories cleaned");

     //   createTestOutputDirectories();
        com.terrelloAPI.utils.logs.LogsManger.info("Directories created");

        loadProperties();
        com.terrelloAPI.utils.logs.LogsManger.info("Properties loaded");


        com.terrelloAPI.utils.report.AllureEnvironmentManager.setEnvironmentVariables();
        com.terrelloAPI.utils.logs.LogsManger.info("Allure environment set");
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("generatiingg noowwww");
        com.terrelloAPI.utils.report.AllureReportGenerator.generateReports(false);
        com.terrelloAPI.utils.report.AllureReportGenerator.copyHistory();
        com.terrelloAPI.utils.logs.LogsManger.info("History copied");
        com.terrelloAPI.utils.report.AllureReportGenerator.generateReports(true);
        String newFileName = com.terrelloAPI.utils.report.AllureReportGenerator.renameReport();
        com.terrelloAPI.utils.report.AllureReportGenerator.openReport(newFileName);
        com.terrelloAPI.utils.logs.LogsManger.info("Test Execution Finished");

    }

    @Override
    public void beforeInvocation(org.testng.IInvokedMethod method, org.testng.ITestResult testResult) {
        if (method.isTestMethod()) {

            LogsManger.info("Test Case " + testResult.getName() + " started");
        }
    }


    @Override
    public void afterInvocation(org.testng.IInvokedMethod method, org.testng.ITestResult testResult) {
        if (method.isTestMethod()) {


            switch (testResult.getStatus()) {
                case ITestResult.FAILURE -> LogsManger.info("Test Method", testResult.getName(), "FAILED");
                case ITestResult.SUCCESS -> LogsManger.info("Test Method", testResult.getName(), "PASSED");
                case ITestResult.SKIP   -> LogsManger.info("Test Method", testResult.getName(), "SKIPPED");
            }

            AllureAttachmentManager.attachLogs();

        } else if (method.isConfigurationMethod()) {
            switch (testResult.getStatus()) {
                case ITestResult.FAILURE -> LogsManger.info("Configuration Method", testResult.getName(), "failed");
                case ITestResult.SUCCESS -> LogsManger.info("Configuration Method", testResult.getName(), "passed");
                case ITestResult.SKIP   -> LogsManger.info("Configuration Method", testResult.getName(), "skipped");
            }
        }
    }




    @Override
    public void onTestSuccess(org.testng.ITestResult result) {
        com.terrelloAPI.utils.logs.LogsManger.info("Test case", result.getName(), "passed");

    }

    @Override
    public void onTestFailure(org.testng.ITestResult result) {
        com.terrelloAPI.utils.logs.LogsManger.info("Test case", result.getName(), "failed");
    }

    @Override
    public void onTestSkipped(org.testng.ITestResult result) {
        com.terrelloAPI.utils.logs.LogsManger.info("Test case", result.getName(), "skipped");

    }



    private void cleanTestOutputDirectories() {
        System.out.println("cleaan worrrkkk");
        com.terrelloAPI.FileUtils.cleanDirectory(com.terrelloAPI.utils.report.AllureConstants.RESULTS_FOLDER.toFile());

        com.terrelloAPI.FileUtils.forceDelete(new File(com.terrelloAPI.utils.logs.LogsManger.LOGS_PATH+File.separator+"execution.log"));
    }

  /*  private void createTestOutputDirectories() {
        FileUtils.createDirectory(ScreenshotsManger.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManger.RECORDING_PATH);
    }*/
}
