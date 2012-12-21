package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * A condition that is satisfied when a sampled variable satisfies some criteria.
 * @param <V> the type of variable to sample
 */
public class SamplerCondition<V> implements Condition {
    private final Sampler<V> variable;
    private final Matcher<? super V> criteria;

    /**
     * Create a condition that is satisfied when the a sample of the variable satisfies the criteria.
     * @param variable the variable to sample
     * @param criteria the criteria to satisfy
     */
    public SamplerCondition(Sampler<V> variable, Matcher<? super V> criteria) {
        this.variable = variable;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        variable.takeSample();
        return criteria.matches(variable.sampledValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(variable)
                .appendText(" ")
                .appendDescriptionOf(criteria);
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        criteria.describeMismatch(variable.sampledValue(), description);
    }

    /**
     * A factory method for creating {@code SamplerCondition}s.
     */
    public static <V> Condition sampleOf(Sampler<V> variable, Matcher<? super V> criteria) {
        return new SamplerCondition<V>(variable, criteria);
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
