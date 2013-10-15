package com.idymnich.petprojects.downloadmanager.common.utils;

import java.net.URL;

/**
 * Created in 15.10.13, 23:20
 * Author: Игорь
 */
public class DownloadManagerUtils {

    /**
     * Gets file name from URL.
     * @param url File URL
     * @return File name
     */
    public static String getFileNameFromUrl(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("Input URL is null!");
        }
        String fileName = url.getFile();

        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }
}
