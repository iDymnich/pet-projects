package com.idymnich.petprojects.downloadmanager.api.implementation;

import com.idymnich.petprojects.downloadmanager.api.DownloadManagerServiceApi;

/**
 * Created in 14.10.13, 23:08
 * Author: Игорь
 */
public abstract class DownloadManager implements DownloadManagerServiceApi {
    protected enum DownloadStatus {
        DOWNLOADING,
        PAUSE,
        STOP;
    }
}
