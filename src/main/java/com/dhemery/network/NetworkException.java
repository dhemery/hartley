package com.dhemery.network;

import java.net.URL;

/**
 * Reports a problem communicating with a resource.
 */
public class NetworkException extends RuntimeException {
    public NetworkException(String explanation, Throwable cause) {
        super(explanation, cause);
    }

    public NetworkException(Resource resource, String explanation, Throwable cause) {
        this(resource.url(), explanation, cause);
    }

    public NetworkException(URL url, String explanation, Throwable cause) {
        this("URL " + url + ' ' + explanation, cause);
    }
}
