package com.dhemery.configuring.options;

import com.dhemery.core.DefaultValue;
import com.dhemery.core.Constrained;
import com.dhemery.core.UnaryOperator;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.allOf;

/**
 * Static utility methods to create option operators.
 */
public class OptionExpressions {
    public  static UnaryOperator<String> requiring(Matcher<? super String>... criteria) {
        return new Constrained<String>(compound(criteria));
    }

    public static UnaryOperator<String> defaultingTo(String defaultValue) {
        return new DefaultValue(defaultValue);
    }

    public static UnaryOperator<String> trimmed() {
        return new TrimString();
    }

    private static Matcher<? super String> compound(Matcher<? super String>[] criteria) {
        return criteria.length == 1 ? criteria[0] : allOf(criteria);
    }

}
