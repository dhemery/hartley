package com.dhemery.network;

/**
 * A factory that creates resources.
 */
public interface ResourceFactory {
    /**
     * Create a resource for the given protocol, host, port, and path.
     */
    Resource resource(String protocol, String host, int port, String path);
}
