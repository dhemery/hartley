package com.dhemery.publishing.tests;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ASubscriptionRegistry {
    private class Publication1{}
    private class Publication2{}

    @Test
    public void hasNoSubscriptionsForNonSubscriber() {
        Object nonSubscriber = new Object();
        assertThat(forSubscriber(nonSubscriber), is(empty()));
    }

    @Test
    public void holdsSubscriptionsBySubscriber() {
        Object subscriber = new Object();
        Class<?> publication = Publication1.class;
        subscribe(subscriber, publication);
        assertThat(forSubscriber(subscriber), hasItem(publication));
    }

    @Test
    public void distinguishesSubscriptionsBySubscriber() {
        Object subscriber1 = new Object();
        Object subscriber2 = new Object();
        subscribe(subscriber1, Publication1.class);
        subscribe(subscriber2, Publication2.class);
        assertThat(forSubscriber(subscriber1), hasItem(Publication1.class));
        assertThat(forSubscriber(subscriber2), hasItem(Publication2.class));
        assertThat(forSubscriber(subscriber1), not(hasItem(Publication2.class)));
        assertThat(forSubscriber(subscriber2), not(hasItem(Publication1.class)));
    }

    private Map<Object,Set<Class<?>>> subscriptions = new HashMap<Object, Set<Class<?>>>();

    public void subscribe(Object subscriber, Class<?> publication) {
        ensureSubscriber(subscriber);
        subscriptions.get(subscriber).add(publication);
    }

    private void ensureSubscriber(Object subscriber) {
        if(subscriptions.containsKey(subscriber)) return;
        subscriptions.put(subscriber, new HashSet<Class<?>>());
    }

    public Set<Class<?>> forSubscriber(Object subscriber) {
        ensureSubscriber(subscriber);
        return subscriptions.get(subscriber);
    }
}
