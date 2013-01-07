package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ATrimmed {
    @Test
    public void yieldsNullForANullOperand() {
        assertThat(new Trimmed().operate(null), is(nullValue()));
    }

    @Test
    public void trimsANonNullStringOperand() {
        assertThat(new Trimmed().operate("   foo   "), is("foo"));
    }
}