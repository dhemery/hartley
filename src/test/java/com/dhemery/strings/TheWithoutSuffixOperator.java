package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheWithoutSuffixOperator {
    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(new WithoutSuffix("ignored").operate(null), is(nullValue()));
    }

    @Test
    public void yieldsOperandIfOperandDoesNotEndWithSuffix() {
        String operand = "foo";
        String suffix = "bar";
        assertThat(new WithoutSuffix(suffix).operate(operand), is(operand));
    }

    @Test
    public void removesSuffixOperandIfOperandEndsWithSuffix() {
        String operand = "foo bar";
        String suffix = " bar";
        assertThat(new WithoutSuffix(suffix).operate(operand), is("foo"));
    }
}
