package com.dhemery.polling;

import com.dhemery.core.Sampler;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * A condition that is satisfied when its sample of a variable satisfies some criteria.
 * @param <V> the type of sampled variable
 */
public class SamplingCondition<V> implements Condition {
    private final Sampler<V> sampler;
    private final Matcher<? super V> criteria;

    /**
     * Create a condition that is satisfied when its sample of a variable satisfies some criteria.
     * @param sampler the sampler that samples the value
     * @param criteria the criteria to satisfy
     */
    public SamplingCondition(Sampler<V> sampler, Matcher<? super V> criteria) {
        this.sampler = sampler;
        this.criteria = criteria;
    }

    /**
     * Sample the variable and evaluate whether the sample satisfies the condition's criteria.
     * @return whether the sampled value satisfies the condition's criteria
     */
    @Override
    public boolean isSatisfied() {
        sampler.takeSample();
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
