package com.twixttechnologies.tjss.model.network.request.extensions;

/**
 * @author Sony Raj on 25-10-17.
 */

public interface UploadStatusListener {
    void onStatusChanged(@Progress.UploadStatus int status);
}