package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;

public class Trimmed implements OptionFilter {
    @Override
    public String of(Option option) {
        String value = option.value();
        return value == null ?  value : value.trim();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("trimmed");
    }
}
