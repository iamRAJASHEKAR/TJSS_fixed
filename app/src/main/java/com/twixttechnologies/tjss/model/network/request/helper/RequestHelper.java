package com.twixttechnologies.tjss.model.network.request.helper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 07-08-17.
 */

public class RequestHelper {

    private RequestHelper() {
        //no instance
    }
    public static MultipartBody.Part multiPartBodyPart(java.io.File file, String parameterName) {
        return MultipartBody.Part.createFormData(parameterName, file == null ? "noFile" : file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file == null ? new java.io.File("") : file));
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }


    public static MultipartBody.Part getMultipartBodyPart(String parameter, File file) {
        return MultipartBody.Part.createFormData(parameter, file == null ? "no-name" : file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file == null ? new File("") : file));
    }

    public static MultipartBody.Part getMultiPartString(String parameter, String value) {
        return MultipartBody.Part.createFormData(parameter, value);
    }

}
