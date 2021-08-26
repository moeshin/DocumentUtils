package com.moeshin.util.document.app;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DirUtil {

    private static final String TAG = "DirUtil";
    private final File dir;

    public DirUtil(File dir) {
        this.dir = dir;
        if (!dir.exists() && !dir.mkdirs()) {
            Log.e(TAG, "DirUtil: create dir failed: " + dir);
        }
    }

    public File getDir() {
        return dir;
    }

    public void createFile(String name) {
        File file = new File(dir, name);
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile()) {
                throw new FileNotFoundException();
            }
        } catch (IOException e) {
            Log.e(TAG, "createFile: create file failed: " + file, e);
        }
    }
}
