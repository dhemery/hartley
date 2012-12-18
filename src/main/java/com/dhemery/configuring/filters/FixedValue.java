package com.dhemery.configuring.filters;

import com.dhemery.configuring.OptionFilter;
import com.dhemery.configuring.Options;

public class FixedValue implements OptionFilter {
    private final String fixedValue;

    public FixedValue(String fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public String apply(Options source, String name) {
        return fixedValue;
    }
}
