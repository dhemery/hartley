package com.dhemery.configuring;

class Trimmed implements OptionFilter {
    @Override
    public String valueOf(Option option) {
        String value = option.value();
        return value == null ?  value : value.trim();
    }
}
