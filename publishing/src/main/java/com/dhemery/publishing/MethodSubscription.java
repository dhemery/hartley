package com.dhemery.publishing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A subscription to be fulfilled by calling a method.
 */
public class MethodSubscription implements Subscription {
    private final Object subscriber;
    private final Method method;

    /**
     * Create a subscription to be delivered by invoking the given method on the subscriber.
     */
    public MethodSubscription(Object subscriber, Method method) {
        this.subscriber = subscriber;
        this.method = method;
    }

    @Override
    public void deliver(Object publication) {
        try {
            method.invoke(subscriber, publication);
        } catch (IllegalAccessException ignore) {
        } catch (InvocationTargetException ignore) {
        }
    }

    @Override
    public String toString() {
        return "MethodSubscription{" +
                "subscriber=" + subscriber +
                ", method=" + method +
                '}';
    }
}
