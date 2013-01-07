package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class AStringTrimmer {
    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(new StringTrimmer().operate(null), is(nullValue()));
    }

    @Test
    public void trimsANonNullOperand() {
        assertThat(new StringTrimmer().operate("   foo   "), is("foo"));
    }
}