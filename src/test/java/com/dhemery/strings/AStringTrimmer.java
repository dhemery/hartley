package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class AStringTrimmer {
    @Test
    public void returnssNullIfOperandIsNull() {
        assertThat(new StringTrimmer().operate(null), is(nullValue()));
    }

    @Test
    public void trimsANonNullStringOperand() {
        assertThat(new StringTrimmer().operate("   foo   "), is("foo"));
    }
}