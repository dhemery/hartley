package com.dhemery.configuring.filtering;

import com.dhemery.configuring.Option;
import com.dhemery.configuring.OptionFilter;
import org.hamcrest.Description;

public class WithPrefix implements OptionFilter {
    private final String prefix;

    public WithPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String of(Option subject) {
        String value = subject.value();
        if(value == null) return prefix;
        return value.startsWith(prefix) ? value : prefix + value;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with prefix ").appendText(prefix);
    }
}
