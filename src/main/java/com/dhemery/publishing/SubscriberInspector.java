package com.dhemery.publishing;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Discovers the subscription methods on an object.
 */
public class SubscriberInspector {
    /**
     * Report the subscription methods declared on the subscriber.
     */
    public Set<Method> subscriptionsOn(Object subscriber) {
        SubscriptionMethodFilter filter = new SubscriptionMethodFilter();
        Class<?> subscriberClass = subscriber.getClass();
        List<Method> methods = Arrays.asList(subscriberClass.getMethods());
        Set<Method> subscriptions = new HashSet<Method>();
        for (Method method : methods) {
            if (filter.accepts(method)) {
                subscriptions.add(method);
            }
        }
        return subscriptions;
    }
}