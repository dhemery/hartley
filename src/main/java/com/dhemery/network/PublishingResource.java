package com.dhemery.network;

import com.dhemery.network.events.*;
import com.dhemery.publishing.Publisher;

import java.net.URL;

/**
 * Wraps a resource and publishes events about its communications.
 * <ul>
 * <li>Before each call to {@link #get()}, the wrapper publishes a {@link WillSendGet} event.</li>
 * <li>After each call to {@code get()}, the wrapper publishes a {@link GetResponded} event.</li>
 * <li>Before each call to {@link #put(String)}, the wrapper publishes a {@link WillSendPut} event.</li>
 * <li>After each call to {@code put(String)}, the wrapper publishes a {@link PutResponded} event.</li>
 * </ul>
 */
public class PublishingResource implements Resource {
    private final Publisher publisher;
    private final Resource resource;

    /**
     * Create a wrapper that uses the given publisher to publish events about the given resource.
     */
    public PublishingResource(Publisher publisher, Resource resource){
        this.publisher = publisher;
        this.resource = resource;
    }

    @Override
    public URL url() {
        return resource.url();
    }

    @Override
    public String get() {
        publisher.publish(new WillSendGet(resource));
        String response = resource.get();
        publisher.publish(new GetResponded(resource, response));
        return response;
    }

    @Override
    public String put(String message) {
        publisher.publish(new WillSendPut(resource, message));
        String response = resource.put(message);
        publisher.publish(new PutResponded(resource, message, response));
        return response;
    }
}
