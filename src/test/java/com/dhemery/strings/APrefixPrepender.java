package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class APrefixPrepender {
    @Test
    public void returnsNullIfOperandIsNull() {
        assertThat(new PrefixPrepender("ignored").operate(null), is(nullValue()));
    }

    @Test
    public void prependsPrefixIfOperandDoesNotStartWithPrefix() {
        String prefix = "foo";
        String operand = "bar";
        assertThat(new PrefixPrepender(prefix).operate(operand), is("foobar"));
    }

    @Test
    public void yieldsOperandIfOperandAlreadyHasPrefix() {
        String prefix = "foo";
        String operand = "foo bar";
        assertThat(new PrefixPrepender(prefix).operate(operand), is(operand));
    }
}
