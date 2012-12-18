package com.dhemery.configuring;

import java.util.Set;

public class FilteringOptions implements Options {
    private final Options source;
    private final OptionFilter filter;

    public FilteringOptions(Options source, OptionFilter filter) {
        this.source = source;
        this.filter = filter;
    }

    @Override
    public Set<String> names() {
        return source.names();
    }

    @Override
    public String option(String name) {
        return filter.apply(source, name);
    }
}
