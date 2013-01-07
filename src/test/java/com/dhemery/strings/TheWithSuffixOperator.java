package com.dhemery.strings;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheWithSuffixOperator {
    private final UnaryOperator<String> withSuffixBar = new WithSuffix("bar");

    @Test
    public void describesItself() {
        assertThat(withSuffixBar.toString(), is("with suffix " + Diagnostic.descriptionOf("bar")));
    }

    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(withSuffixBar.operate(null), is(nullValue()));
    }

    @Test
    public void appendsSuffixIfOperandDoesNotEndWithSuffix() {
        assertThat(withSuffixBar.operate("foo"), is("foobar"));
    }

    @Test
    public void yieldsOperandIfOperandEndsWithSuffix() {
        assertThat(withSuffixBar.operate("foo bar"), is("foo bar"));
    }
}
