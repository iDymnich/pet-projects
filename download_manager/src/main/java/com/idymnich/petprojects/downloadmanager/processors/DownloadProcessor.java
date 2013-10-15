package com.idymnich.petprojects.downloadmanager.processors;

import com.idymnich.petprojects.downloadmanager.api.Download;
import com.idymnich.petprojects.downloadmanager.api.implementation.DownloadManager;
import com.idymnich.petprojects.downloadmanager.common.helpers.exceptions.DownloadManagerException;
import com.idymnich.petprojects.downloadmanager.common.utils.DownloadManagerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created in 15.10.13, 22:47
 * Author: Игорь
 */
public class DownloadProcessor implements Download {

    private final static int MAX_BUFFER_SIZE = 1024;
    private final static String RANGE_PROPERTY = "Range";
    private final static String ACCESS_MODE = "rw";

    private int downloadBytesCounter;
    private int contentSize;
    private String directoryPath;
    private String fileUrl;
    private DownloadManager.DownloadStatus downloadStatus;
    private Thread currentThread;

    public DownloadProcessor(String directoryPath, String fileUrl) {
        this.directoryPath = directoryPath;
        this.fileUrl = fileUrl;
    }

    /**
     * Starts selected download process.
     */
    @Override
    public void onStart() {
        if (downloadStatus == DownloadManager.DownloadStatus.DOWNLOADING ||
                downloadStatus == DownloadManager.DownloadStatus.DOWNLOADED) {
            throw new DownloadManagerException(String.format("Cant start %s download, because download status: %s",
                    fileUrl, downloadStatus));
        }
        if (currentThread == null || !currentThread.isAlive()) {
            currentThread = new Thread(this, fileUrl);
        }

        currentThread.start();
    }

    /**
     * Pauses selected download process.
     */
    @Override
    public void onPause() {
        downloadStatus = DownloadManager.DownloadStatus.STOP;
        downloadBytesCounter = 0;
        if (currentThread.isAlive()) {
            currentThread.interrupt();
        }
    }

    /**
     * Stoped selected download process without resume opportunity.
     */
    @Override
    public void onStop() {
        downloadStatus = DownloadManager.DownloadStatus.STOP;
        downloadBytesCounter = 0;
        if (currentThread.isAlive()) currentThread.interrupt();
    }

    /**
     * Sets current download status
     *
     * @param downloadStatus DownloadStatus
     */
    @Override
    public void setDownloadStatus(DownloadManager.DownloadStatus downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    /**
     * Gets current download status
     *
     * @return DownloadStatus
     */
    @Override
    public DownloadManager.DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        RandomAccessFile file = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            /*
            Here we send to server count of bytes which were downloaded (id resumed for example
             */
            connection.setRequestProperty(RANGE_PROPERTY, String.format("bytes=%d-", downloadBytesCounter));
            connection.connect();

            /*
            Verifying if connection has normal state && content does not empty
             */
            int responseCode = connection.getResponseCode();
            if (responseCode / 100 != 2 || (contentSize = connection.getContentLength()) < 1) {
                throw new DownloadManagerException(String.format("Invalid remote resource initialization!." +
                        "Response code: %d, content size: %d", responseCode, contentSize));
            }

            downloadStatus = DownloadManager.DownloadStatus.DOWNLOADING;
            file = new RandomAccessFile(DownloadManagerUtils.getFileNameFromUrl(url), ACCESS_MODE);
            file.seek(downloadBytesCounter);
            inputStream = connection.getInputStream();

            while (downloadStatus == DownloadManager.DownloadStatus.DOWNLOADING) {
                byte buffer[];
                int sizeLeft;

                if ((sizeLeft = contentSize - downloadBytesCounter) > MAX_BUFFER_SIZE) {
                    buffer = new byte[MAX_BUFFER_SIZE];
                } else {
                    buffer = new byte[sizeLeft];
                }

                int read = inputStream.read(buffer);
                if (read == -1) {
                    break;
                }

                downloadBytesCounter += read;
                file.write(buffer, 0, read);
            }

            downloadStatus = DownloadManager.DownloadStatus.DOWNLOADED;
        } catch (MalformedURLException e) {
            throw new DownloadManagerException("Invalid file url! Can not creates URI instance.");
        } catch (IOException e) {
            throw new DownloadManagerException("Exception during file downloading!", e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }


    }

    @Override
    public String toString() {
        return "DownloadProcessor{" +
                "downloadBytesCounter=" + downloadBytesCounter +
                ", contentSize=" + contentSize +
                ", directoryPath='" + directoryPath + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", downloadStatus=" + downloadStatus +
                '}';
    }
}
