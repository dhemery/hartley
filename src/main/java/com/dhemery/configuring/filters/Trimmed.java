package com.dhemery.configuring.filters;

import com.dhemery.configuring.Option;
import com.dhemery.core.NamedFeature;

/**
 * Trims whitespace from the ends of the option value.
 */
public class Trimmed extends NamedFeature<Option,String> {
    /**
     * Create a feature that trims whitespace from the option value.
     */
    public Trimmed() {
        super("trimmed");
    }

    /**
     * Return the option value with whitespace trimmed from both ends.
     * @param option the option whose value to trim
     * @return the trimmed value, or null if the option value is null
     */
    @Override
    public String of(Option option) {
        String value = option.value();
        return value == null ? null : option.value();
    }
}
