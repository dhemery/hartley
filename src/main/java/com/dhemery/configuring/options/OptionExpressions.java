package com.dhemery.configuring.options;

import com.dhemery.configuring.MaybeUnaryOperator;
import org.hamcrest.Matcher;

/**
 * Static utility methods to create option filters.
 */
public class OptionExpressions {
    public  static MaybeUnaryOperator<String> requiring(final Matcher<String> criteria) {
        return new MaybeUnaryOperator<String>("requiring " + criteria.toString()) {
            @Override
            protected String resultForPresent(String string) {
                if(criteria.matches(string)) return string;
                return null;
            }
        };
    }

    public static MaybeUnaryOperator<String> defaultingTo(final String defaultValue) {
        return new MaybeUnaryOperator<String>("defaulting to " + defaultValue) {
            @Override
            protected String resultForAbsent() {
                return defaultValue;
            }
        };
    }

    public static MaybeUnaryOperator<String> trimmed() {
        return new MaybeUnaryOperator<String>("trimmed"){
            @Override
            protected String resultForPresent(String string) {
                return string.trim();
            }
        };
    }
}
