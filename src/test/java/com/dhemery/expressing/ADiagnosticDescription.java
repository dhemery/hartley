package com.dhemery.expressing;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ADiagnosticDescription {
    @Test
    public void surroundsStringWithQuotes() {
        assertThat(Diagnostic.descriptionOf("foo"), is("\"foo\""));
    }

    @Test
    public void surroundsCharactersWithSingleQuotes() {
        assertThat(Diagnostic.descriptionOf('x'), is("'x'"));
    }

    @Test
    public void describesNullAsNull() {
        assertThat(Diagnostic.descriptionOf(null), is("null"));
    }

    @Test
    public void surroundsNumberWithAngleBrackets() {
        assertThat(Diagnostic.descriptionOf((byte) 1), is("<1b>"));
        assertThat(Diagnostic.descriptionOf((short) 2), is("<2s>"));
        assertThat(Diagnostic.descriptionOf((int) 3), is("<3>"));
        assertThat(Diagnostic.descriptionOf((long) 4), is("<4L>"));
        assertThat(Diagnostic.descriptionOf((float) 5), is("<5.0f>"));
        assertThat(Diagnostic.descriptionOf((double) 6), is("<6.0d>"));
    }

    @Test
    public void describesOtherObjectsWithToString() {
        Object o = new Object(){ @Override public String toString() { return "bloot"; }};
        assertThat(Diagnostic.descriptionOf(o), is("bloot"));
        assertThat(Diagnostic.descriptionOf(false), is("false"));
        assertThat(Diagnostic.descriptionOf(true), is("true"));
    }

}
