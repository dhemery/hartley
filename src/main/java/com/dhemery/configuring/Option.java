package com.dhemery.configuring;

public class Option {
    private final Configuration source;
    private final String optionName;
    private final String optionValue;

    public Option(Configuration source, String optionName, String optionValue) {
        this.source = source;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public Configuration source() {
        return source;
    }

    public String name() {
        return optionName;
    }

    public String value() {
        return optionValue;
    }
}
