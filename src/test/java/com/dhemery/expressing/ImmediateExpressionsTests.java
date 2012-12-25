package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedAction;
import com.dhemery.core.NamedFeature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.dhemery.expressing.ImmediateExpressions.each;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImmediateExpressionsTests {
    @Test
    public void eachTreatsNullAsAnEmptyCollection() {
        Iterable<String> NULL = null;
        assertThat(each(NULL).iterator().hasNext(), is(false));
    }

    @Test
    public void forEachIteratesOverEachMember() {
        Collection<String> results = new ArrayList<String>();
        each(Arrays.asList("a", "b", "c")).forEach(appendTo(results));
        assertThat(results, contains("a", "b", "c"));
    }

    @Test
    public void forEachDoesNotThrowIfActionIsNull() {
        each(Arrays.asList("a", "b", "c")).forEach(null);
    }

    @Test
    public void filterYieldsEachMemberThatMatches() {
        List<String> all = Arrays.asList("fooA", "barB", "fooC", "fooD", "barE", "barF", "fooG");
        assertThat(each(all).filter(beginsWith("foo")), contains("fooA", "fooC", "fooD", "fooG"));
        assertThat(each(all).filter(beginsWith("bar")), contains("barB", "barE", "barF"));
    }

    @Test
    public void transformYieldsATransformationForEachMember() {
        List<String> strings = Arrays.asList("a", "b", "c");
        Iterable<String> transformed = each(strings).transform(toUpperCase());
        assertThat(transformed, contains("A", "B", "C"));
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
}
