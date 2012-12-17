package com.dhemery.configuring;

import java.util.Arrays;

import static com.dhemery.configuring.OptionExpressions.trimmed;

/**
 * Decorates another configuration to trim whitespace from the values of its options.
 * The values in the underlying configuration are not changed.
 * The values are trimmed each time a user reads an option,
 * and each time the trimming configuration is converted to a map or a properties list.
 */
public class TrimmingConfiguration extends FilteringConfiguration {
    public TrimmingConfiguration(Configuration configuration) {
        super(configuration, Arrays.asList(trimmed()));
    }
}
