package com.dhemery.polling.events;

import com.dhemery.core.Feature;
import org.hamcrest.Matcher;

public class FeatureMismatches<S,V> {
    public FeatureMismatches(S subject, Feature<? super S, V> feature, V sample, Matcher<? super V> criteria, int failureCount) {
    }
}
