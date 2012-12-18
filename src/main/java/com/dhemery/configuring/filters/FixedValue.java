package com.dhemery.configuring.filters;

import com.dhemery.configuring.OptionFilter;
import com.dhemery.configuring.Options;

/**
 * Returns a fixed value.
 */
public class FixedValue implements OptionFilter {
    private final String fixedValue;

    /**
     * Create a filter that always returns the given fixed value.
     */
    public FixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    /**
     * Return the filter's fixed value, regardless of the method's arguments.
     */
    @Override
    public String transform(Options source, String name, String value) {
        return fixedValue;
    }
}
