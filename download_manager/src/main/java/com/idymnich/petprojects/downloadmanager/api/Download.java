package com.idymnich.petprojects.downloadmanager.api;

/**
 * Created in 14.10.13, 23:22
 * Author: Игорь
 */
public interface Download extends Runnable {
    void onDownloading();
    void onStart();
    void onPause();
    void onStop();
}
