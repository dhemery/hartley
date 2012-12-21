package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create option filters.
 */
public class OptionExpressions {
    public static Feature<Option,String> defaultingTo(final String defaultValue) {
        return new NamedFeature<Option, String>("defaulting to \"" + defaultValue + '"') {
            @Override
            public String of(Option option) {
                String value = option.value();
                return value == null ? defaultValue : value;
            }
        };
    }

    public static Feature<Option,String> ensuring(Matcher<? super String>... criteria) {
        return ensuring(value(), matcherFor(criteria));
    }

    public static <F> Feature<Option,String> ensuring(Feature<Option,F> feature, Matcher<? super F>... criteria) {
        return new Constraint(feature, matcherFor(criteria));
    }

    public static Feature<Option,String> trimmed() {
        return new NamedFeature<Option, String>("trimmed") {
            @Override
            public String of(Option option) {
                String value = option.value();
                return value == null ? null : value.trim();
            }
        };
    }

    public static Feature<Option,String> value() {
        return new NamedFeature<Option, String>("value") {
            @Override
            public String of(Option option) {
                return option.value();
            }
        };
    }

    public static Matcher<Option> value(Matcher<? super String> valueMatchers) {
        final Matcher<? super String> matcher = matcherFor(valueMatchers);
        return new TypeSafeMatcher<Option>() {
            @Override
            protected boolean matchesSafely(Option option) {
                return matcher.matches(option.value());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value ").appendDescriptionOf(matcher);
            }
        };
    }

    private static <T> Matcher<? super T> matcherFor(Matcher<? super T>... criteria) {
        Matchers.anything();
        if(criteria.length == 1) return criteria[0];
        return allOf(criteria);
    }
}
