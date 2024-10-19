package org.cai.file.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cai584770
 * @date 2024/10/15 19:06
 * @Version
 */
public class FilePath {

    public static List<String> getAllFilePaths(String directoryPath) {
        List<String> filePaths = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files!= null) {
                for (File file : files) {
                    if (file.isFile()) {
                        filePaths.add(file.getAbsolutePath());
                    } else if (file.isDirectory()) {
                        filePaths.addAll(getAllFilePaths(file.getAbsolutePath()));
                    }
                }
            }
        }
        return filePaths;
    }

    public static String extractNameFromFullPath(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int dotIndex = fileName.indexOf('.');
        if (dotIndex!= -1) {
            return fileName.substring(0, dotIndex);
        }
        return null;
    }

    public static String extractParentFolderNameFromFullPath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile!= null) {
                String parentFolderName = parentFile.getName();
                return parentFolderName;
            }
        }
        return null;
    }

    public static void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            boolean mkdirs = folder.mkdirs();
        }
    }

}
