package com.dhemery.configuring;

import java.util.Arrays;
import java.util.List;

public class FilteringOptionsChain {
    public static ModifiableOptions filterChain(ModifiableOptions source, List<OptionFilter> filters) {
        ModifiableOptions chain = source;
        for (OptionFilter filter : filters) chain = new FilteringOptions(chain, filter);
        return chain;
    }

    public static ModifiableOptions filterChain(ModifiableOptions source, OptionFilter[] filters) {
        return filterChain(source, Arrays.asList(filters));
    }
}