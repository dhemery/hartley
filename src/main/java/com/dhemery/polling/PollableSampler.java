package com.dhemery.polling;

import com.dhemery.core.Sampler;
import com.dhemery.polling.events.SampleMatches;
import com.dhemery.polling.events.SampleMismatches;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;

public class PollableSampler<V> extends Pollable {
    private final Sampler<V> variable;
    private final Matcher<? super V> criteria;

    public PollableSampler(Publisher publisher, Sampler<V> variable, Matcher<? super V> criteria) {
        super(publisher);
        this.variable = variable;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        variable.takeSample();
        return criteria.matches(variable.sampledValue());
    }

    @Override
    protected Object failureNotification(int failureCount) {
        return new SampleMatches(variable, criteria, failureCount);
    }

    @Override
    protected Object successNotification(int failureCount) {
        return new SampleMismatches(variable, criteria, failureCount);
    }
}
