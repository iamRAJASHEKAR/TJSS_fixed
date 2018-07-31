package com.twixttechnologies.tjss.utils;

/**
 * @author Sony Raj on 09-09-17.
 */

public interface TransactionCallback<SuccessResult, Error> {

    void onSuccess(SuccessResult result);

    void onFailure(Error error);

}
