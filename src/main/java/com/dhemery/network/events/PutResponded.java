package com.dhemery.network.events;

import com.dhemery.network.Resource;

/**
 * Reports a {@link Resource}'s response to a {@link Resource#put(String) put(String)} request.
 */
public class PutResponded {
    private final Resource resource;
    private final String message;
    private final String response;

    public PutResponded(Resource resource, String message, String response) {
        this.resource = resource;
        this.message = message;
        this.response = response;
    }
    public Resource resource() { return resource; }
    public String message() { return message; }
    public String response() { return response; }
}
