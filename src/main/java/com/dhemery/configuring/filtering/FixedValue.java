package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;

public class FixedValue implements OptionFilter {
    private final String value;

    public FixedValue(String value) {
        this.value = value;
    }

    @Override
    public String of(Option option) {
        return value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(value);
    }
}
