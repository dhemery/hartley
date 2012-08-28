package com.dhemery.network;

import com.dhemery.serializing.Codec;

/**
 * An endpoint that uses a Codec to serialize and deserialize objects.
 */
public class CodecEndpoint implements SerializingEndpoint {
    private final Endpoint endpoint;
    private final Codec codec;

    /**
     * Create an endpoint that uses the Codec to serialize and deserialize
     * objects transmitted through another endpoint.
     * @param endpoint the endpoint through which to send and receive messages
     * @param codec the codec with which to serialize and deserialize objects
     */
    public CodecEndpoint(Endpoint endpoint, Codec codec) {
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public <T> T get(String path, Class<T> responseType) {
        String rawResponse = endpoint.get(path);
        return codec.decode(rawResponse, responseType);
    }

    @Override
    public <T> T put(String path, Object body, Class<T> responseType) {
        String encodedBody = codec.encode(body);
        String encodedResponse = endpoint.put(path, encodedBody);
        return codec.decode(encodedResponse, responseType);
    }
}
