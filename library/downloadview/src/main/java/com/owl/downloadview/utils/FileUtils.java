
package com.owl.downloadview.utils;

import android.os.Environment;

import com.owl.downloadview.data.Task;

import java.io.File;

public class FileUtils {

    private final static String TEMP_SUFFIX = ".tmp";

    private static String dirPath = null;

    public static File getTaskFile(Task task) {
        checkDirExists();

        String filePath = null;

        if (task.getInfo() == null) {
            // 防止异常 一般不会出现
            filePath = dirPath + System.currentTimeMillis();
        } else {
            filePath = dirPath + task.getInfo().getFileName();
        }

        File file = new File(filePath);

        return file;

    }

    public static File getTaskTempFile(Task task) {
        checkDirExists();

        String filePath = null;
        if (task.getInfo() == null) {
            // 防止异常 一般不会出现
            filePath = dirPath + System.currentTimeMillis();
        } else {
            filePath = dirPath + task.getInfo().getFileName();
        }

        File file = new File(filePath + TEMP_SUFFIX);

        return file;
    }

    private static void checkDirExists() {
        String storageRootPath = Environment.getExternalStorageDirectory().getPath();
        dirPath = storageRootPath + "/downloads/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

    }

}
