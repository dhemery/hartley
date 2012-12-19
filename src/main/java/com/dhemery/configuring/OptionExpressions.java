package com.dhemery.configuring;

import com.dhemery.configuring.filters.Constraint;
import com.dhemery.configuring.filters.Defined;
import com.dhemery.configuring.filters.Nil;
import com.dhemery.configuring.filters.Trimmed;
import org.hamcrest.Matcher;

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

    /**
     * Report whether the option value is null.
     */
    public static Matcher<Option> nil() {
        return new Nil();
    }

    /**
     * Return the current value if the option satisfies the criteria.
     * Throw an exception if the option does not satisfy the criteria.
     * Note that if the option does not satisfy the criteria,
     * this filter ignores any value supplied by previous filters.
     */
    public static Transformation<Option> require(Matcher<? super Option> constraint) {
        return new Constraint(constraint);
    }

    /**
     * Trim whitespace from the ends of the option value.
     */
    public static Transformation<Option> trimmed() {
        return new Trimmed();
    }
}
