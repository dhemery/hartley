package com.dhemery.network;

/**
 * An endpoint that delegates the creation of resources to a factory.
 */
public class ResourceFactoryBasedEndpoint implements Endpoint {
    private final String protocol;
    private final String host;
    private final int port;
    private final ResourceFactory create;

    /**
     * An endpoint at the given protocol, host, and port,
     * which delegates creation of resources to the given factory.
     */
    public ResourceFactoryBasedEndpoint(String protocol, String host, int port, ResourceFactory resources) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        create = resources;
    }

    @Override
    public String protocol() {
        return protocol();
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String get(String path) {
        return resourceFor(path).get();
    }

    @Override
    public String put(String path, String message) {
        return resourceFor(path).put(message);
    }

    private Resource resourceFor(String path) {
        return create.resource(protocol, host, port,  path);
    }

    @Override
    public String toString() {
        return protocol + "://" + host + ':' + port;
    }
}
