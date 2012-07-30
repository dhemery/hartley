package com.dhemery.network.events;

import com.dhemery.network.Resource;

/**
 * Reports that a {@link Resource} will send a {@link Resource#put(String) put(String)} request.
 */
public class WillSendPut {
    private final Resource resource;
    private final String message;

    public WillSendPut(Resource resource, String message) {
        this.resource = resource;
        this.message = message;
    }
    public Resource resource() { return resource; }
    public String message() { return message; }
}
