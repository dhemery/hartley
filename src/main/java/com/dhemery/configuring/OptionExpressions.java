package com.dhemery.configuring;

import com.dhemery.configuring.filtering.*;
import org.hamcrest.Matcher;

/**
 * Factory methods to create option matchers and filters.
 */
public class OptionExpressions {
    public static OptionFilter or(OptionFilter fallbackValue) {
        return fallbackValue;
    }

    public static OptionFilter or(String defaultValue) {
        return new FixedValue(defaultValue);
    }

    /**
     * Report whether the configuration defines the option.
     */
    public static Matcher<Option> defined() {
        return new Defined();
    }

    public static Matcher<Option> nil() {
        return new NullString();
    }

    public static OptionFilter either(Matcher<? super Option> criteria, OptionFilter fallback) {
        return new Either(criteria, fallback);
    }

    /**
     * Return the current value if the option satisfies the criteria.
     * Throw an exception if the option does not satisfy the criteria.
     * Note that if the option does not satisfy the criteria,
     * this filter ignores any value supplied by previous filters.
     */
    public static OptionFilter require(Matcher<? super Option> constraint) {
        return new Constrained(constraint);
    }

    /**
     * Return the current value if the configuration defines the option..
     * Throw an exception if the option is not defined.
     * Note that if the option is not defined,
     * this filter ignores any value supplied by previous filters.
     */
    public static OptionFilter required() {
        return require(defined());
    }

    /**
     * Trim whitespace from the ends of the option value.
     */
    public static OptionFilter trimmed() {
        return new Trimmed();
    }
}
