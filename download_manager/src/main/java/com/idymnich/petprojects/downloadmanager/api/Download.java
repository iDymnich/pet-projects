package com.idymnich.petprojects.downloadmanager.api;

import com.idymnich.petprojects.downloadmanager.api.implementation.DownloadManager;

/**
 * Created in 14.10.13, 23:22
 * Author: Игорь
 */
public interface Download extends Runnable {

    /**
     * Starts selected download process.
     */
    void onStart();

    /**
     * Pauses selected download process.
     */
    void onPause();

    /**
     * Stoped selected download process without resume opportunity.
     */
    void onStop();

    /**
     * Sets current download status
     * @param downloadStatus DownloadStatus
     */
    void setDownloadStatus(DownloadManager.DownloadStatus downloadStatus);

    /**
     * Gets current download status
     * @return DownloadStatus
     */
    DownloadManager.DownloadStatus getDownloadStatus();
}
