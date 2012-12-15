package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Returns the option value if the option satisfies some constraint.
 * Returns a fallback value if the option does not satisfy the constraint.
 */
public class Either implements OptionFilter {
    private final Matcher<? super Option> constraint;
    private final OptionFilter fallbackValue;

    public Either(Matcher<? super Option> constraint, OptionFilter fallbackValue) {
        this.constraint = constraint;
        this.fallbackValue = fallbackValue;
    }

    @Override
    public String of(Option option) {
        return constraint.matches(option)  ? option.value() : fallbackValue.of(option);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("either ")
                .appendDescriptionOf(constraint)
                .appendText(" or ")
                .appendDescriptionOf(fallbackValue);
    }
}
