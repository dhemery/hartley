package com.dhemery.strings;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheWithoutSuffixOperator {
    private final UnaryOperator<String> withoutSuffixBar  = new WithoutSuffix("bar");

    @Test
    public void describesItself() {
        assertThat(withoutSuffixBar.toString(), is("without suffix " + Diagnostic.descriptionOf("bar")));
    }

    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(withoutSuffixBar.operate(null), is(nullValue()));
    }

    @Test
    public void yieldsOperandIfOperandDoesNotEndWithSuffix() {
        assertThat(withoutSuffixBar.operate("foo"), is("foo"));
    }

    @Test
    public void removesSuffixOperandIfOperandEndsWithSuffix() {
        assertThat(withoutSuffixBar.operate("foo bar"), is("foo "));
    }
}
