package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.Transformation;
import org.hamcrest.Description;

/**
 * Trims whitespace from the ends of the option value.
 */
public class Trimmed implements Transformation<Option> {
    /**
     * Return the option value with whitespace trimmed from both ends.
     * @param option the option whose value to trim
     * @return the trimmed value, or null if the option value is null
     */
    @Override
    public Option of(Option option) {
        String value = option.value();
        String newValue = value == null ? value : value.trim();
        return new TransformedOption(option, this, newValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("trimmed");
    }
}
