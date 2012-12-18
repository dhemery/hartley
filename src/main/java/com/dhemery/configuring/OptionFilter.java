package com.dhemery.configuring;

/**
 * Transforms the values of options.
 * <p>Most filters will transform the supplied value
 * without regard to the option's name or source.</p>
 * <p>The supplied value may have been transformed by filters applied earlier,
 * and may not be the same as the current value from the source.</p>
 * <p>The supplied value may be {@code null}.</p>
 * <p>The name and source are provided
 * in case they are required for the transformation
 * or for diagnostic purposes.
 */
public interface OptionFilter {
    /**
     * Return the value of the option, transformed by this filter.
     * @param source the source that defines the option's untransformed value
     * @param name the name of the option
     * @param value the value from the source, transformed by any previously applied filters
     * @return the value of the option, transformed by this filter
     */
    String transform(Options source, String name, String value);
}
