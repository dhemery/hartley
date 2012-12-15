package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;

public class WithoutPrefix implements OptionFilter {
    private final String prefix;

    public WithoutPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String of(Option subject) {
        String value = subject.value();
        if(value == null) return value;
        return value.startsWith(prefix) ?  value.substring(prefix.length()) : value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("without prefix ").appendText(prefix);
    }
}
