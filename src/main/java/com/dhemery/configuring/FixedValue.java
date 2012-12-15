package com.dhemery.configuring;

public class FixedValue implements OptionFilter {
    private final String value;

    public FixedValue(String value) {
        this.value = value;
    }

    @Override
    public String valueOf(Option option) {
        return value;
    }
}
