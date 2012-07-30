package com.dhemery.network.events;

import com.dhemery.network.Resource;

/**
 * Reports that a {@link Resource} will send a {@link Resource#get() get()} request.
 */
public class WillSendGet {
    private final Resource resource;

    public WillSendGet(Resource resource) {
        this.resource = resource;
    }
    public Resource resource() { return resource; }
}
