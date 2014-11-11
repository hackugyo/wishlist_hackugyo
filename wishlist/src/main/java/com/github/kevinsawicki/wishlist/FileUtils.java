package com.github.kevinsawicki.wishlist;

import java.io.File;

public class FileUtils {

    public static int deleteAllFiles(String dirPath) {
        int count = 0;

        final File dir = new File(dirPath);
        if (!dir.exists()) {
            LogUtils.w("Not Exist:" + dirPath);
            return 0;
        }
        if (dir.isFile()) {
            dir.delete();
            return 1;
        } else if (dir.isDirectory()) {
            final File[] files = dir.listFiles();
            if (files == null) {
                LogUtils.d("No files at " + dir.getAbsolutePath());
                return 0;
            }
            for (final File file : files) {
                if (!file.delete()) {
                    LogUtils.e("Can't remove " + file.getAbsolutePath());
                } else {
                    count += 1;
                }
            }
        }
        return count;
    }
}
