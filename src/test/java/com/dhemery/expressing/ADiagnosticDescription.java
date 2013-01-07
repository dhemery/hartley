package com.dhemery.expressing;

import org.junit.Test;

import java.util.Arrays;

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
    public void describesPrimitiveNumbersWithAngleBracketsAndtypeMarker() {
        assertThat(Diagnostic.descriptionOf((byte) 1), is("<1b>"));
        assertThat(Diagnostic.descriptionOf((short) 2), is("<2s>"));
        assertThat(Diagnostic.descriptionOf((int) 3), is("<3>"));
        assertThat(Diagnostic.descriptionOf((long) 4), is("<4L>"));
        assertThat(Diagnostic.descriptionOf((float) 5), is("<5.0f>"));
        assertThat(Diagnostic.descriptionOf((double) 6), is("<6.0d>"));
    }

    @Test
    public void surroundsArraysWithBracketsAndSeparatesItemsWithCommas() {
        String[] strings = {"a", "b", "c", "d"};
        assertThat(Diagnostic.descriptionOf(strings), is("[\"a\",\"b\",\"c\",\"d\"]"));
    }

    @Test
    public void describesArrayItemsDiagnostically() {
        Object[] items = {"monkey", 'z', (byte)1, (short)2, (int)3, (long)5, (float)8, (double)13, null, false, true};
        assertThat(Diagnostic.descriptionOf(items), is("[\"monkey\",'z',<1b>,<2s>,<3>,<5L>,<8.0f>,<13.0d>,null,false,true]"));
    }

    @Test
    public void surroundsIterablesWithBracketsAndSeparatesItemsWithCommas() {
        String[] strings = {"a", "b", "c", "d"};
        assertThat(Diagnostic.descriptionOf(Arrays.asList(strings)), is("[\"a\",\"b\",\"c\",\"d\"]"));
    }

    @Test
    public void describesIterableItemsDiagnostically() {
        Object[] items = {"monkey", 'z', (byte)1, (short)2, (int)3, (long)5, (float)8, (double)13, null, false, true};
        assertThat(Diagnostic.descriptionOf(Arrays.asList(items)), is("[\"monkey\",'z',<1b>,<2s>,<3>,<5L>,<8.0f>,<13.0d>,null,false,true]"));
    }

    @Test
    public void describesOtherValuesWithStringValue() {
        Object object = new Object();
        assertThat(Diagnostic.descriptionOf(object), is(String.valueOf(object)));
        assertThat(Diagnostic.descriptionOf(false), is(String.valueOf(false)));
        assertThat(Diagnostic.descriptionOf(true), is(String.valueOf(true)));
        assertThat(Diagnostic.descriptionOf(null), is(String.valueOf((Object)null)));
    }
}
