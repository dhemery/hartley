package com.dhemery.publishing;

/**
 * A publisher that does nothing.
 */
public class NullPublisher implements Publisher {
    @Override public void publish(Object publication) {}
}
