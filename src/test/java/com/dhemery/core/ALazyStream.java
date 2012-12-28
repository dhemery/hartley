package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.dhemery.expressing.ImmediateExpressions.assertThat;
import static com.dhemery.expressing.ImmediateExpressions.streamOf;
import static org.hamcrest.Matchers.*;

public class ALazyStream {
    @Test
    public void treatsNullAsAnEmptyIterable() {
        Iterable<String> NULL = null;
        assertThat(streamOf(NULL), yield(), is(empty()));
    }

    @Test
    public void forEach_iteratesOverEachMember() {
        Collection<String> results = new ArrayList<String>();
        streamOf(Arrays.asList("a", "b", "c")).forEach(appendTo(results));
        assertThat(results, contains("a", "b", "c"));
    }

    @Test
    public void forEach_doesNotThrowIfActionIsNull() {
        streamOf(Arrays.asList("a", "b", "c")).forEach(null);
    }

    @Test
    public void filter_yieldsEachMemberThatMatches() {
        List<String> all = Arrays.asList("fooA", "barB", "fooC", "fooD", "barE", "barF", "fooG");
        assertThat(streamOf(all).filter(beginsWith("foo")), yield(), contains("fooA", "fooC", "fooD", "fooG"));
        assertThat(streamOf(all).filter(beginsWith("bar")), yield(), contains("barB", "barE", "barF"));
    }

    @Test
    public void map_yieldsAMappedValueForEachMember() {
        List<String> strings = Arrays.asList("a", "b", "c");
        assertThat(streamOf(strings).map(toUpperCase()), yield(), contains("A", "B", "C"));
    }

    private Matcher<String> beginsWith(final String prefix) {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                return item.startsWith(prefix);
            }

            @Override public void describeTo(Description description) {}
        };
    }

    private static Feature<String,String> toUpperCase() {
        return new NamedFeature<String, String>("") {
            @Override
            public String of(String subject) {
                return subject.toUpperCase();
            }
        };
    }

    private static Action<String> appendTo(final Collection<String> collector) {
        return new NamedAction<String>("") {
            @Override
            public void actOn(String subject) {
                collector.add(subject);
            }
        };
    }

    private Feature<LazyStream<String>, Collection<String>> yield() {
        return new NamedFeature<LazyStream<String>, Collection<String>>("yield") {
            @Override
            public Collection<String> of(LazyStream<String> stream) {
                return stream.into(new ArrayList<String>());
            }
        };
    }
}
