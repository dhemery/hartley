package com.dhemery.polling.events;

import com.dhemery.core.Feature;
import org.hamcrest.Matcher;

/**
 * Reports that a feature matched the criteria during a poll.
 * @param <S> the type of subject
 * @param <V> the type of feature
 */
public class FeatureMatches<S,V> {
    private final S subject;
    private final Feature<? super S, V> feature;
    private final V value;
    private final Matcher<? super V> criteria;
    private final int failureCount;

    public FeatureMatches(S subject, Feature<? super S, V> feature, V value, Matcher<? super V> criteria, int failureCount) {
        this.subject = subject;
        this.feature = feature;
        this.value = value;
        this.criteria = criteria;
        this.failureCount = failureCount;
    }

    public S subject() {
        return subject;
    }

    public Feature<? super S, V> feature() {
        return feature;
    }

    public V value() {
        return value;
    }

    public Matcher<? super V> criteria() {
        return criteria;
    }

    public int failureCount() {
        return failureCount;
    }
}
