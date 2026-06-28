package com.terrelloAPI;


import com.terrelloAPI.utils.dataReader.PropertyReader;
import com.terrelloAPI.utils.logs.LogsManger;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static final String USER_DIR= com.terrelloAPI.utils.dataReader.PropertyReader.getProperty("user.dir")+File.separator;
    private FileUtils() {
    }

public static void createDirectory(String Path){
        try {
            File file= new File(USER_DIR + Path);
           if(!file.exists()){
               file.mkdirs();
               com.terrelloAPI.utils.logs.LogsManger.info("Directory created successfully: " +Path);
           }
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error creating directory: " + Path, e.getMessage());
        }
}

    public static void renameFile(String oldName, String newName) {
        try {
            File oldFile = new File(oldName); // use as-is if absolute
            if (!oldFile.isAbsolute()) {
                oldFile = new File(USER_DIR + oldName);
            }

            File newFile = new File(newName);
            if (!newFile.isAbsolute()) {
                //  put in same directory as old file
                newFile = new File(oldFile.getParent(), newName);
            }

            if (!oldFile.exists()) {
                com.terrelloAPI.utils.logs.LogsManger.error("File not found: " + oldFile.getAbsolutePath());
                return;
            }

            if (oldFile.renameTo(newFile)) {
                com.terrelloAPI.utils.logs.LogsManger.info("File renamed to: " + newFile.getAbsolutePath());
            } else {
                com.terrelloAPI.utils.logs.LogsManger.error("Failed to rename file: " + oldFile.getAbsolutePath());
            }
        } catch (Exception e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Error renaming file: " + oldName, e.getMessage());
        }
    }




    public static void forceDelete(File file) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(file);
            com.terrelloAPI.utils.logs.LogsManger.info("File deleted successfully: " + file.getAbsolutePath());
        } catch (IOException e) {
            com.terrelloAPI.utils.logs.LogsManger.error("Failed to delete file: " + file.getAbsolutePath() + " due to: " + e.getMessage());
        }
    }









    // cleaning Directory
    public static void cleanDirectory(File file)
        {
            try {
                org.apache.commons.io.FileUtils.deleteQuietly(file);
            }
            catch (Exception e) {
                com.terrelloAPI.utils.logs.LogsManger.error("Failed to clean directory: " + file.getAbsolutePath(), e.getMessage());
            }
        }
}
