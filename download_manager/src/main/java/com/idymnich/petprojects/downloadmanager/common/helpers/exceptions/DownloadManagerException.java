package com.idymnich.petprojects.downloadmanager.common.helpers.exceptions;

/**
 * Created in 16.10.13, 0:06
 * Author: Игорь
 */
public class DownloadManagerException extends RuntimeException {
    private String description;
    private Throwable throwable;

    public DownloadManagerException(String description) {
        this(description, null);
    }

    public DownloadManagerException(String description, Throwable throwable) {
        this.description = description;
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "DownloadManagerException{" +
                "description='" + description + '\'' +
                ", throwable=" + throwable +
                '}';
    }
}
