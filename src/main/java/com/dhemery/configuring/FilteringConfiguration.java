package com.dhemery.configuring;

import com.dhemery.configuring.filtering.OptionFilterList;

import java.util.*;

public class FilteringConfiguration extends AbstractConfiguration {
    private final Configuration configuration;
    private final OptionFilterList filters;

    public FilteringConfiguration(Configuration configuration, List<OptionFilter> filters) {
        this.configuration = configuration;
        this.filters = new OptionFilterList(filters);
    }

    @Override
    public void define(String name, String value) {
        configuration.define(name, value);
    }

    @Override
    public boolean defines(String name) {
        return configuration.defines(name);
    }

    @Override
    public Set<String> names() {
        return configuration.names();
    }

    @Override
    public String option(String name) {
        return filteredOption(name, configuration.option(name), filters);
    }
}
