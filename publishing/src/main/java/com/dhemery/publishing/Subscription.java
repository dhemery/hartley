package com.dhemery.publishing;

/**
 * Represents a single subscriber's subscription to a single type of publication.
 */
public interface Subscription {
    /**
     * Deliver the publication to the subscriber.
     */
    void deliver(Object publication);
}
