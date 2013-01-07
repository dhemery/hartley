package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ASuffixAppender {
    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(new SuffixAppender("ignored").operate(null), is(nullValue()));
    }

    @Test
    public void appendsSuffixIfOperandDoesNotEndWithSuffix() {
        String operand = "foo";
        String suffix = "bar";
        assertThat(new SuffixAppender(suffix).operate(operand), is("foobar"));
    }

    @Test
    public void yieldsOperandIfOperandEndsWithSuffix() {
        String operand = "foo bar";
        String suffix = " bar";
        assertThat(new SuffixAppender(suffix).operate(operand), is(operand));
    }
}
