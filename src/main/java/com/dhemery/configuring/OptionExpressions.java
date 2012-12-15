package com.dhemery.configuring;

import org.hamcrest.Matcher;

/**
 * Factory methods to create option matchers and filters.
 */
public class OptionExpressions {
    /**
     * Return a fixed value.
     */
    public static OptionFilter defaultValue(String defaultValue) {
        return new FixedValue(defaultValue);
    }

    /**
     * Report whether the configuration defines the option.
     */
    private static Matcher<Option> defined() {
        return new Defined();
    }

    /**
     * Return the current value if the option satisfies the criteria.
     * Throw an exception if the option does not satisfy the criteria.
     * Note that if the option does not satisfy the criteria,
     * this filter ignores any value supplied by previous filters.
     */
    public static OptionFilter require(Matcher<? super Option> criteria) {
        return new Constraint(criteria);
    }

    /**
     * Apply a recovery filter if the option does not satisfy the criteria.
     */
    public static OptionFilter require(Matcher<? super Option> criteria, OptionFilter recovery) {
        return new RecoverableConstraint(criteria, recovery);
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
