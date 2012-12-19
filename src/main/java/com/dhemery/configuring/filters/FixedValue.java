package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.core.NamedFeature;

/**
 * Returns a fixed value, regardless of the option.
 */
public class FixedValue extends NamedFeature<Option,String> {
    private final String fixedValue;

    /**
     * Create a filter that returns the fixed value, regardless of the option.
     */
    public FixedValue(String fixedValue) {
        super("fixed value " + fixedValue);
        this.fixedValue = fixedValue;
    }

    /**
     * Return the filter's fixed value, regardless of the option.
     */
    @Override
    public String of(Option option) {
        return fixedValue;
    }
}
