package com.dhemery.polling;

import com.dhemery.core.Condition;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ProbingCondition<V> implements Condition {
    private final Probe<? extends V> probe;
    private final Matcher<? super V> criteria;

    public ProbingCondition(Probe<? extends V> probe, Matcher<? super V> criteria) {
        this.probe = probe;
        this.criteria = criteria;
    }

    @Override
    public boolean isSatisfied() {
        probe.probe();
        return criteria.matches(probe.value());
    }

    @Override
    public void discribeDissatisfactionTo(Description description) {
        criteria.describeMismatch(probe.value(), description);
    }

    @Override
    public void describeTo(Description description) {
        criteria.describeTo(description);
    }
}
