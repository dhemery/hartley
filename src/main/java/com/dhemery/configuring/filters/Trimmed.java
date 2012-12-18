package com.dhemery.configuring.filters;

import com.dhemery.configuring.OptionFilter;
import com.dhemery.configuring.Options;

public class Trimmed implements OptionFilter {
    @Override
    public String apply(Options source, String name) {
        String option = source.option(name);
        return option == null ? null : option.trim();
    }
}
