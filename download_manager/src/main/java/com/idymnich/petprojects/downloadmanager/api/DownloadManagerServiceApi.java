package com.idymnich.petprojects.downloadmanager.api;

/**
 * Created in 14.10.13, 22:41
 * Author: Игорь
 */
public interface DownloadManagerServiceApi {

    /**
     * This method sets Download manager status.
     *
     * @param uri uri of the download which should change download status.
     * @param status Download status
     */
    void setDownloadManagerStatus(String uri, String status);

    /**
     * This method removes concrete download from downloads pool.
     *
     * @param uri uri of the download which should be removed
     *
     * @return remove status
     */
    boolean removeDownload(String uri);

    /**
     * Adds concrete download to the downloads pool.
     *
     * @param uri The URI of future download.
     * @param directoryPath The path of directory, where will be saved downloaded file.
     *
     * @return Concrete download
     */
    Download addDownload(String uri, String directoryPath);
}
