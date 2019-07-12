package com.mareike.solrsearch.localDirectories;

import java.io.File;
import java.io.FileFilter;

public class MyFile {
    private final File mFile;

    public MyFile(final File pFile) {
        mFile = pFile;
    }

    public boolean isDirectory() {
        return mFile.isDirectory();
    }

    public MyFile[] listFiles() {
        //only directories are listed and hidden directories are excluded
        final File[] files = mFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isHidden()) {
                    return false;
                }
                return pathname.isDirectory();
            }
        });
        if (files == null)
            return null;
        if (files.length < 1)
            return new MyFile[0];

        final MyFile[] ret = new MyFile[files.length];
        for (int i = 0; i < ret.length; i++) {
            final File f = files[i];
            ret[i] = new MyFile(f);
        }

        return ret;
    }

    public File getFile() {
        return mFile;
    }

    @Override public String toString(){
        return mFile.getName();
    }
}