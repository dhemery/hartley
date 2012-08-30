package com.dhemery.core;

import com.dhemery.core.Condition;
import com.dhemery.core.Sampler;
import org.hamcrest.Matcher;

public class SamplerCondition<V> implements Condition {
    public SamplerCondition(Sampler<V> variable, Matcher<? super V> criteria) {
    }

    @Override
    public boolean isSatisfied() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String description() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String failureDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
