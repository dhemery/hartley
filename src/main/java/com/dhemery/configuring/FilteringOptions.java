package com.dhemery.configuring;

import java.util.Set;

public class FilteringOptions implements ModifiableOptions {
    private final ModifiableOptions source;
    private final OptionFilter filter;

    public FilteringOptions(ModifiableOptions source, OptionFilter filter) {
        this.source = source;
        this.filter = filter;
    }

    @Override
    public void define(String name, String value) {
        source.define(name, value);
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
