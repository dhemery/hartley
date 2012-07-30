package com.dhemery.publishing;

import java.lang.reflect.Method;
import java.util.*;

/**
 * A channel through which subscribers receive publications via methods annotated by {@link Subscribe}.
 */
public class MethodSubscriptionChannel implements Channel {
    private final Map<Class<?>,Set<Subscription>> subscriptionsByPublicationType = new HashMap<Class<?>, Set<Subscription>>();
    private final SubscriberInspector discover = new SubscriberInspector();

    /**
     * Create a method subscription channel with no subscribers.
     */
    public MethodSubscriptionChannel(){}

    @Override
    public void subscribe(Object subscriber) {
        Set<Method> methods = discover.subscriptionsOn(subscriber);
        for(Method method : methods) {
            Class<?> publicationType = method.getParameterTypes()[0];
            subscriptionsForPublicationType(publicationType).add(new MethodSubscription(subscriber, method));
        }
    }

    @Override
    public void subscribe(Object subscriber1, Object... others) {
        subscribe(subscriber1);
        for(Object subscriber : Arrays.asList(others)) {
            subscribe(subscriber);
        }
    }

    @Override
    public void unsubscribe(Object subscriber) {
    }

    @Override
    public void unsubscribe(Object subscriber1, Object... others) {
    }

    @Override
    public void publish(Object publication) {
        Set<Subscription> subscriptions = subscriptionsForPublication(publication);
        for(Subscription subscription : subscriptions) {
            subscription.deliver(publication);
        }
    }

    private Set<Subscription> subscriptionsForPublication(Object publication) {
        Class<?> publicationType = publication.getClass();
        return subscriptionsForPublicationType(publicationType);
    }

    private Set<Subscription> subscriptionsForPublicationType(Class<?> publicationType) {
        if(!subscriptionsByPublicationType.containsKey(publicationType)) {
            Set<Subscription> subscriptions =  new HashSet<Subscription>();
            subscriptionsByPublicationType.put(publicationType, subscriptions);
        }
        return subscriptionsByPublicationType.get(publicationType);
    }
}
