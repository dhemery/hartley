package com.dhemery.polling.events;

import com.dhemery.core.Sampler;
import org.hamcrest.Matcher;

public class SampleMatches<V> {
    public SampleMatches(Sampler<V> variable, Matcher<? super V> criteria, int failureCount) {
    }
}
