package com.terrelloAPI.utils;

import com.terrelloAPI.utils.logs.LogsManger;

import java.io.IOException;

public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            ProcessBuilder pb = new ProcessBuilder(commandParts);
            pb.redirectErrorStream(true); // merge stderr into stdout

            Process process = pb.start();

            // ✅ Read output to see WHY it fails
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                com.terrelloAPI.utils.logs.LogsManger.error("Command failed with exit code: " + exitCode + " | Output: " + output);
            } else {
                com.terrelloAPI.utils.logs.LogsManger.info("Command succeeded | Output: " + output);
            }
        } catch (IOException | InterruptedException e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Failed to execute: " + String.join(" ", commandParts), e.getMessage());
        }
    }
}