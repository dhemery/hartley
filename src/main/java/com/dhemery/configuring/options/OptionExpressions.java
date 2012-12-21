package com.dhemery.configuring.options;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.filters.*;
import com.dhemery.core.Feature;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create option filters.
 */
public class OptionExpressions {
    /**
     * Report whether the option is defined.
     */
    public static Matcher<Option> defined() {
        return new Defined();
    }

    public static Matcher<Option> option(Matcher<? super Option>... criteria) {
        return new OptionConstraint(matcherFor(criteria));
    }

    /**
     * Return the current value if the option satisfies the criteria.
     * Throw an exception if the option does not satisfy the criteria.
     * Note that if the option does not satisfy the criteria,
     * this filter ignores any value supplied by previous filters.
     */
    public static Feature<Option,String> require(Matcher<? super Option>... criteria) {
        return new Constraint(matcherFor(criteria));
    }

    /**
     * Trim whitespace from the ends of the option value.
     */
    public static Feature<Option,String> trimmed() {
        return new Trimmed();
    }

    public static Matcher<Option> value(Matcher<? super String>... criteria) {
        return new ValueConstraint(matcherFor(criteria));
    }

    private static <T> Matcher<? super T> matcherFor(Matcher<? super T>... criteria) {
        if(criteria.length == 1) return criteria[0];
        return allOf(criteria);
    }
}
