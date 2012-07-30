package com.dhemery.network.events;

import com.dhemery.network.Resource;

/**
 * Reports a {@link Resource}'s response to a {@link Resource#get() get()} request.
 */
public class GetResponded {
    private final Resource resource;
    private final String response;

    public GetResponded(Resource resource, String response) {
        this.resource = resource;
        this.response = response;
    }
    public Resource resource() { return resource; }
    public String response() { return response; }
}
