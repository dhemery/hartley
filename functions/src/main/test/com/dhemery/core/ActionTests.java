package com.dhemery.core;

import org.hamcrest.Description;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ActionTests {
    @Test
    public void actOn_actsOnTheSubject() {
        final Set<String> actedOn = new HashSet<>();

        Action<String> action = new Action<String>() {
            @Override public void actOn(String subject) { actedOn.add(subject) ;}
            @Override public void describeTo(Description description) {}
        };

        action.actOn("Foo");
        assertThat(actedOn, contains("Foo"));
    }

    @Test
    public void default_accept_delegatesTo_actOn() {
        final Set<String> actedOn = new HashSet<>();

        Action<String> action = new Action<String>() {
            @Override public void actOn(String subject) { actedOn.add(subject) ;}
            @Override public void describeTo(Description description) {}
        };


        action.accept("Bar");
        assertThat(actedOn, contains("Bar"));
    }
}
