package com.dhemery.configuring;

import java.util.List;

public class Option {
    private final Configuration configuration;
    private final String optionName;
    private String optionValue;

    public Option(Configuration configuration, String optionName, String optionValue) {
        this.configuration = configuration;
        this.optionName = optionName;
        this.optionValue = optionValue;
    }

    public Configuration configuration() {
        return configuration;
    }

    public String name() {
        return optionName;
    }

    public String value() {
        return optionValue;
    }

    public void apply(List<OptionFilter> filters) {
        for(OptionFilter filtered : filters) {
            optionValue = filtered.of(this);
        }
    }
}
