package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * A condition that is satisfied when a sampled value matches criteria.
 * @param <V> the type of sampled value
 */
public class SamplerCondition<V> implements Condition {
    private final Sampler<V> sampler;
    private final Matcher<? super V> criteria;

    /**
     * Create a condition that is satisfied when the sampled value matches the criteria.
     */
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
    public void describeDissatisfactionTo(Description description) {
        criteria.describeMismatch(sampler.sampledValue(), description);
    }
}
