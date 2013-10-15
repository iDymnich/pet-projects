package com.idymnich.petprojects.downloadmanager;

import com.idymnich.petprojects.downloadmanager.api.Download;
import com.idymnich.petprojects.downloadmanager.api.implementation.DownloadManager;
import com.idymnich.petprojects.downloadmanager.common.helpers.exceptions.DownloadManagerException;
import com.idymnich.petprojects.downloadmanager.processors.DownloadProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created in 14.10.13, 23:11
 * Author: Игорь
 */
public class DownloadManagerService extends DownloadManager {

    /**
     * service instance *
     */
    private static DownloadManagerService instance;
    /**
     * The pool of downloads *
     */
    private Map<String, Download> downloadsPool = new HashMap<String, Download>();

    private DownloadManagerService() {
    }

    /**
     * This method sets Download manager status.
     *
     * @param uri    uri of the download which should change download status.
     * @param status Download status
     */
    @Override
    public void setDownloadManagerStatus(String uri, String status) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method removes concrete download from downloads pool.
     *
     * @param uri uri of the download which should be removed
     * @return remove status
     */
    @Override
    public boolean removeDownload(String uri) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adds concrete download to the downloads pool.
     *
     * @param uri           The URI of future download.
     * @param directoryPath The path of directory, where will be saved downloaded file.
     * @return Concrete download
     */
    @Override
    public Download addDownload(String uri, String directoryPath) {
        Download download = new DownloadProcessor(directoryPath, uri);
        downloadsPool.put(uri, download);
        try {
            download.onStart();
        } catch (DownloadManagerException e) {

        }

        return download;
    }

    /**
     * Gets service instance.
     *
     * @return DownloadManagerService
     */
    public static DownloadManagerService getInstance() {
        if (instance == null) {
            synchronized (DownloadManagerService.class) {
                instance = new DownloadManagerService();
            }
        }

        return instance;
    }
}
