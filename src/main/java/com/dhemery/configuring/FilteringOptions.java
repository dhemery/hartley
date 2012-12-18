package com.dhemery.configuring;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Applies a sequence of filters to the options defined in another set.
 */
public class FilteringOptions implements ModifiableOptions {
    private final ModifiableOptions source;
    private final List<OptionFilter> filters;

    /**
     * Create a set of options backed by the source and filtered through a sequence of filters.
     * @param source a source that defines a set of options
     * @param filters filters to transform the values retrieved from the source
     */
    public FilteringOptions(ModifiableOptions source, List<OptionFilter> filters) {
        this.source = source;
        this.filters = filters;
    }

    /**
     * Create a set of options backed by the source and filtered through a sequence of filters.
     * @param source a source that defines a set of options
     * @param filters filters to transform the values retrieved from the source
     */
    public FilteringOptions(ModifiableOptions source, OptionFilter[] filters) {
        this(source, Arrays.asList(filters));
        Collections.unmodifiableList(this.filters);
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
        String option = source.option(name);
        for(OptionFilter filter : filters) option = filter.transform(source, name, option);
        return option;
    }
}
