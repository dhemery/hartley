package com.dhemery.configuring.filters;

import com.dhemery.configuring.OptionFilter;
import com.dhemery.configuring.Options;

/**
 * Returns the option value with whitespace trimmed from both ends.
 */
public class Trimmed implements OptionFilter {
    /**
     * Return the given value with whitespace trimmed from both ends.
     * @param value the value to trim
     * @return the trimmed value, or null if the given value is null
     */
    @Override
    public String transform(Options source, String name, String value) {
        return value == null ? null : value.trim();
    }
}
