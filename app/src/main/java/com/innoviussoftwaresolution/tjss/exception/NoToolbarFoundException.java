package com.innoviussoftwaresolution.tjss.exception;

/**
 * @author Sony Raj on 30/06/17.
 */

public class NoToolbarFoundException extends RuntimeException {

    public NoToolbarFoundException(String message) {
        super(message);
    }

    public NoToolbarFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
