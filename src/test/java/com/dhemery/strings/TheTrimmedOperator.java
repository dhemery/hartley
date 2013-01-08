package com.dhemery.strings;

import com.dhemery.core.UnaryOperator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheTrimmedOperator {
    private final UnaryOperator<String> trimmed = new Trimmed();

    @Test
    public void describesItself() {
        assertThat(trimmed.toString(), is("trimmed"));

    }

    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(trimmed.operate(null), is(nullValue()));
    }

    @Test
    public void trimsANonNullOperand() {
        assertThat(trimmed.operate("   foo   "), is("foo"));
    }
}
