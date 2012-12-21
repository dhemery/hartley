package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.core.Feature;
import org.hamcrest.Description;

/**
 * Trims whitespace from the ends of the option value.
 */
public class Trimmed implements Feature<Option,String> {
    /**
     * Return the option value with whitespace trimmed from both ends.
     * @param option the option whose value to trim
     * @return the trimmed value, or null if the option value is null
     */
    @Override
    public String of(Option option) {
        String value = option.value();
        return value == null ? value : value.trim();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("trimmed");
    }
}
