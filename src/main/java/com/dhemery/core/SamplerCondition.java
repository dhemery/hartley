package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class SamplerCondition<V> implements Condition {
    private final Sampler<V> sampler;
    private final Matcher<? super V> criteria;

    public SamplerCondition(Sampler<V> sampler, Matcher<? super V> criteria) {
        this.sampler = sampler;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        sampler.takeSample();
        return criteria.matches(sampler.sampledValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(sampler)
                .appendText(" ")
                .appendDescriptionOf(criteria);
    }

    @Override
    public void describeFailureTo(Description description) {
        criteria.describeMismatch(sampler.sampledValue(), description);
    }
}
