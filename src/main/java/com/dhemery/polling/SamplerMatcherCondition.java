package com.dhemery.polling;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class SamplerMatcherCondition<V> implements Condition {
    private final Sampler<V> sampler;
    private final Matcher<? super V> criteria;

    public SamplerMatcherCondition(Sampler<V> sampler, Matcher<? super V> criteria) {
        this.sampler = sampler;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        sampler.sample();
        return criteria.matches(sampler.sampledValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(sampler).appendText(" ").appendDescriptionOf(criteria);
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        criteria.describeMismatch(sampler.sampledValue(), description);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
