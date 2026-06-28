package com.terrelloAPI.utils.report;


import com.terrelloAPI.utils.OSUtils;
import com.terrelloAPI.utils.TerminalUtils;
import com.terrelloAPI.utils.logs.LogsManger;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {


    private static class LazyHolder
        {
            static final String VERSION = resolveVersion(); //2.34.1 no usages

            private static String resolveVersion() {
               try {
                   String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                           .followRedirects(true).execute().url().toString();
                   return url.split("/tag/")[1];
               } catch (Exception e) {
                   throw new IllegalStateException("Unable to resolve allure version",e);
               }
        }


        }
    public static void downloadAndExtract() {
        try {
            String version = LazyHolder.VERSION;
            Path extractionDir = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version);
            if (Files.exists(extractionDir)) {
                LogsManger.info("Allure binaries already exist.");
                return;
            }

            // Give execute permissions to the binary if not on Windows
            if (!OSUtils.getOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", AllureConstants.USER_DIR.toString());
            }


            Path zipPath = downloadZip(version);
            extractZip(zipPath);

            LogsManger.info("Allure binaries downloaded and extracted.");
            // Give execute permissions to the binary if not on Windows
            if (!OSUtils.getOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", getExecutable().toString());
            }
            // Clean up the zip file after extraction
            Files.deleteIfExists(Files.list(AllureConstants.EXTRACTION_DIR).filter(p -> p.toString().endsWith(".zip")).findFirst().orElseThrow());

        } catch (Exception e) {
            LogsManger.error("Error downloading or extracting binaries", e.getMessage());
        }
    }

    public static Path getExecutable() {
        String version = LazyHolder.VERSION;
        Path binaryPath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version, "bin", "allure");
        return OSUtils.getOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }

    private static Path downloadZip(String version) {
        try {
            //https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.34.1/allure-commandline-2.34.1.zip
            String url = AllureConstants.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            Path zipFile = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");
            if (!Files.exists(zipFile)) {
                Files.createDirectories(AllureConstants.EXTRACTION_DIR);
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                     OutputStream out = Files.newOutputStream(zipFile)) {
                    in.transferTo(out);
                } catch (Exception e) {
                    LogsManger.error("Invalid URL for Allure download: ", e.getMessage());
                }
            }
            return zipFile;
        } catch (Exception e) {
            LogsManger.error("Error downloading Allure zip file", e.getMessage());
            return Paths.get("");
        }
    }
    //Extract ZIP file for Allure
    private static void extractZip(Path zipPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), File.separator, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        } catch (Exception e) {
            LogsManger.error("Error extracting Allure zip file", e.getMessage());
        }
    }

}
