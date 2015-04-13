package com.dhemery.core;

import org.hamcrest.Description;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FeatureTests {
    @Test
    public void of_appliesTheFunctionToTheSubject() {
        Feature<String,Integer> length = new Feature<String, Integer>() {
            @Override  public Integer of(String subject) { return subject.length(); }
            @Override public void describeTo(Description description) {}
        };

        assertThat(length.of("Foooooooo"), is(9));
    }

    @Test
    public void default_apply_delegatesTo_of() {
        Feature<String,Integer> length = new Feature<String, Integer>() {
            @Override  public Integer of(String subject) { return subject.length(); }
            @Override public void describeTo(Description description) {}
        };

        assertThat(length.apply("Foooooooo"), is(9));
    }
}
