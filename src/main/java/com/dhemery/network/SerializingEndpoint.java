package com.dhemery.network;

/**
 * Wraps an endpoint to allow it to send and return serializable objects.
 */
public interface SerializingEndpoint {
    /**
     * Send an HTTP GET request to the given path and deserialize the response into an object.
     * @param path the path to GET
     * @param responseType the class of the type of object to return
     * @param <T> the type of object to return
     * @return the object created by deserializing the GET response body into type {@code T}
     */
    <T> T get(String path, Class<T> responseType);

    /**
     * Serialize the given object, send it via HTTP PUT
     * and deserialize the response into an object.
     * @param path the path to which ot send the message
     * @param body the object to serialize as the body of the message
     * @param responseType the class of the type of object to return
     * @param <T> the type of object to return
     * @return the object created by deserializing the PUT response body into type {@code T}
     */
    <T> T put(String path, Object body, Class<T> responseType);
}
