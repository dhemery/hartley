package com.dhemery.polling.events;

import com.dhemery.core.Sampler;
import org.hamcrest.Matcher;

public class SampleMismatches<V> {
    public SampleMismatches(Sampler<V> variable, Matcher<? super V> criteria, int failureCount) {
    }
}
