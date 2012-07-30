package com.dhemery.network;

import com.dhemery.publishing.Publisher;

/**
 * A resource factory that wraps another factory's {@link Resource}s
 * in {@link PublishingResource} wrappers.
 */
public class PublishingResourceFactory implements ResourceFactory {
    private final Publisher publisher;
    private final ResourceFactory create;

    /**
     * Create resource factory that wraps another factory's {@link Resource}s
     * in {@link PublishingResource} wrappers.
     */
    public PublishingResourceFactory(Publisher publisher, ResourceFactory resources){
        this.publisher = publisher;
        create = resources;
    }

    @Override
    public Resource resource(String protocol, String host, int port, String path) {
        Resource resource = create.resource(protocol, host, port, path);
        return new PublishingResource(publisher, resource);
    }
}
