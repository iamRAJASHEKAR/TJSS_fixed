package com.innoviussoftwaresolution.tjss.model.network.request;

/**
 * @author Sony Raj on 04-08-17.
 */

public interface RequestCallback<T> {

    void onSuccess(T result);

    void onFailure(String reason);

}
