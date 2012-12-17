package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionFilterList implements OptionFilter {
    private final List<OptionFilter> filters = new ArrayList<OptionFilter>();

    public OptionFilterList(List<OptionFilter> filters) {
        this.filters.addAll(filters);
    }

    public OptionFilterList(OptionFilter... filters) {
        this(Arrays.asList(filters));
    }

    @Override
    public String of(Option option) {
        for(OptionFilter filteredValue : filters) {
            option = new Option(option.source(), option.name(), filteredValue.of(option));
        }
        return option.value();
    }

    @Override
    public void describeTo(Description description) {
        if(filters.isEmpty()) return;
        description.appendDescriptionOf(filters.get(0));
        for(int i = 1 ; i < filters.size() ; i++) {
            description.appendText(" and ").appendDescriptionOf(filters.get(i));
        }
    }
}
