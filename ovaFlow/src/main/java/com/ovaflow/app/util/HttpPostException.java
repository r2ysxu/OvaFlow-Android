package com.ovaflow.app.util;

/**
 * An exception for signaling a problem with HTTP Post.
 */
@SuppressWarnings("serial")
public class HttpPostException extends Exception {
    public HttpPostException(Exception e) {
        super(e);
    }
}

