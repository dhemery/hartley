package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import com.dhemery.core.Maybe;
import com.dhemery.core.NamedFeature;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.dhemery.core.Maybe.absent;
import static com.dhemery.core.Maybe.definitely;
import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create option filters.
 */
public class OptionExpressions {
    public static Feature<Option,Maybe<String>> defaultingTo(final String defaultValue) {
        return new NamedFeature<Option, Maybe<String>>("defaulting to \"" + defaultValue + '"') {
            @Override
            public Maybe<String> of(Option option) {
                for(String value : option.value()) { return definitely(value); }
                return definitely(defaultValue);
            }
        };
    }

    public static Feature<Option,Maybe<String>> requiring(Feature<Option,Maybe<String>> feature, Matcher<? super String>... criteria) {
        return new Constraint(feature, matcherFor(criteria));
    }

    public static Feature<Option,Maybe<String>> trimmed() {
        return new NamedFeature<Option, Maybe<String>>("trimmed") {
            @Override
            public Maybe<String> of(Option option) {
                for(String value : option.value()) return definitely(value.trim());
                return absent();
            }
        };
    }

    public static Feature<Option,Maybe<String>> value() {
        return new NamedFeature<Option, Maybe<String>>("value") {
            @Override
            public Maybe<String> of(Option option) {
                return option.value();
            }
        };
    }

    public static Matcher<Option> value(Matcher<? super String> valueMatchers) {
        Matcher<? super String> valueMatcher = matcherFor(valueMatchers);
        final Matcher<Maybe<String>> matcher = maybeMatcher(valueMatcher);
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
        if(criteria.length == 1) return criteria[0];
        return allOf(criteria);
    }

    private static Matcher<Maybe<String>> maybeMatcher(final Matcher<? super String> criteria) {
        return new TypeSafeMatcher<Maybe<String>>() {
            @Override
            protected boolean matchesSafely(Maybe<String> item) {
                for(String value : item) return criteria.matches(value);
                return false;
            }

            @Override
            public void describeTo(Description description) {
                criteria.describeTo(description);
            }
        };
    }
}
