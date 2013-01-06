package com.dhemery.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DescriptionOf {
    @Test
    public void surroundsStringWithQuotes() {
        assertThat(Descriptions.descriptionOf("foo"), is("\"foo\""));
    }

    @Test
    public void surroundsCharactersWithSingleQuotes() {
        assertThat(Descriptions.descriptionOf('x'), is("'x'"));
    }

    @Test
    public void describesNullAsNull() {
        assertThat(Descriptions.descriptionOf(null), is("null"));
    }

    @Test
    public void surroundsNumberWithAngleBrackets() {
        assertThat(Descriptions.descriptionOf((byte) 1), is("<1b>"));
        assertThat(Descriptions.descriptionOf((short) 2), is("<2s>"));
        assertThat(Descriptions.descriptionOf((int) 3), is("<3>"));
        assertThat(Descriptions.descriptionOf((long) 4), is("<4L>"));
        assertThat(Descriptions.descriptionOf((float) 5), is("<5.0f>"));
        assertThat(Descriptions.descriptionOf((double) 6), is("<6.0d>"));
    }

    @Test
    public void describesOtherObjectsWithToString() {
        Object o = new Object(){ @Override public String toString() { return "bloot"; }};
        assertThat(Descriptions.descriptionOf(o), is("bloot"));
        assertThat(Descriptions.descriptionOf(false), is("false"));
        assertThat(Descriptions.descriptionOf(true), is("true"));
    }

}
