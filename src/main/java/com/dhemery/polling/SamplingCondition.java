package com.dhemery.polling;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * A condition that is satisfied when its sample of a variable satisfies some criteria.
 * @param <V> the type of sampled variable
 */
public class SamplingCondition<V> implements Condition {
    private final Sampled<V> sampled;
    private final Matcher<? super V> criteria;

    /**
     * Create a condition that is satisfied when its sample of a variable satisfies some criteria.
     * @param sampled the sampled that samples the value
     * @param criteria the criteria to satisfy
     */
    public SamplingCondition(Sampled<V> sampled, Matcher<? super V> criteria) {
        this.sampled = sampled;
        this.criteria = criteria;
    }

    /**
     * Sample the variable and evaluate whether the sample satisfies the condition's criteria.
     * @return whether the sampled value satisfies the condition's criteria
     */
    @Override
    public boolean isSatisfied() {
        sampled.sample();
        return criteria.matches(sampled.value());
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(sampled).appendText(" ").appendDescriptionOf(criteria);
    }

    @Override
    public void describeDissatisfactionTo(Description description) {
        criteria.describeMismatch(sampled.value(), description);
    }

    @Override
    public String toString() {
        return StringDescription.toString(this);
    }
}
