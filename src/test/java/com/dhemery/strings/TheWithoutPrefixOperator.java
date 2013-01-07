package com.dhemery.strings;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheWithoutPrefixOperator {
    private final UnaryOperator<String> withoutPrefixFoo = new WithoutPrefix("foo");

    @Test
    public void describesItself() {
        assertThat(withoutPrefixFoo.toString(), is("without prefix " + Diagnostic.descriptionOf("foo")));
    }

    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(withoutPrefixFoo.operate(null), is(nullValue()));
    }

    @Test
    public void removesPrefixIfOperandStartsWithPrefix() {
        assertThat(withoutPrefixFoo.operate("foo bar"), is(" bar"));
    }

    @Test
    public void yieldsOperandIfOperandDoesNotStartWithPrefix() {
        assertThat(withoutPrefixFoo.operate("bar"), is("bar"));
    }
}
