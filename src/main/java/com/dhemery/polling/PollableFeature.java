package com.dhemery.polling;

import com.dhemery.core.Feature;
import com.dhemery.polling.events.FeatureMatches;
import com.dhemery.polling.events.FeatureMismatches;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;

public class PollableFeature<S, V> extends Pollable {
    private final S subject;
    private final Feature<? super S, V> feature;
    private final Matcher<? super V> criteria;
    V sample;

    public PollableFeature(Publisher publisher, S subject, Feature<? super S, V> feature, Matcher<? super V> criteria) {
        super(publisher);
        this.subject = subject;
        this.feature = feature;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        sample = feature.of(subject);
        return criteria.matches(sample);
    }

    @Override
    protected Object failureNotification(int failureCount) {
        return new FeatureMatches(subject, feature, sample, criteria, failureCount);
    }

    @Override
    protected Object successNotification(int failureCount) {
        return new FeatureMismatches(subject, feature, sample, criteria, failureCount);
    }
}
