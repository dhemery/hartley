package com.dhemery.expressing;

import com.dhemery.core.Condition;
import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * A condition that is satisfied when a sampler's sampled value satisfies some criteria.
 * @param <R> the type of the sampled value
 */
public class SamplingCondition<R> implements Condition {
    private final Sampler<R> variable;
    private final Matcher<? super R> criteria;

    /**
     * Create a condition that is satisfied when the sampler's sampled value satisfies the criteria.
     * @param sampler the sampler whose sampled value to evaluate
     * @param criteria the criteria to satisfy
     */
    public SamplingCondition(Sampler<R> sampler, Matcher<? super R> criteria) {
        this.variable = sampler;
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
}
