package com.dhemery.strings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class APrefixRemover {
    @Test
    public void returnsNullIfOperandIsNull() {
        assertThat(new PrefixRemover("ignored").operate(null), is(nullValue()));
    }

    @Test
    public void removesPrefixIfOperandStartsWithPrefix() {
        String prefix = "foo ";
        String operand = "foo bar";
        assertThat(new PrefixRemover(prefix).operate(operand), is("bar"));
    }

    @Test
    public void yieldsOperandIfOperandDoesNotStartWithPrefix() {
        String prefix = "foo";
        String operand = "bar";
        assertThat(new PrefixRemover(prefix).operate(operand), is(operand));
    }
}
