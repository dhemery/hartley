package com.dhemery.strings;

import com.dhemery.core.Diagnostic;
import com.dhemery.core.UnaryOperator;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TheWithPrefixOperator {
    private final UnaryOperator<String> withPrefixFoo = new WithPrefix("foo");

    @Test
    public void describesItself() {
        assertThat(new WithPrefix("foo").toString(), is("with prefix " + Diagnostic.descriptionOf("foo")));
    }

    @Test
    public void yieldsNullIfOperandIsNull() {
        assertThat(withPrefixFoo.operate(null), is(nullValue()));
    }

    @Test
    public void prependsPrefixIfOperandDoesNotStartWithPrefix() {
        assertThat(withPrefixFoo.operate("bar"), is("foobar"));
    }

    @Test
    public void yieldsOperandIfOperandStartsWithPrefix() {
        assertThat(withPrefixFoo.operate("foo bar"), is("foo bar"));
    }
}
