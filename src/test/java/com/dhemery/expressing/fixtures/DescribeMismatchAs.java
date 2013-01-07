package com.dhemery.expressing.fixtures;

import org.hamcrest.Description;
import org.jmock.api.*;

public class DescribeMismatchAs implements org.jmock.api.Action {
    private final String mismatchDescription;

    public DescribeMismatchAs(String mismatchDescription) {
        this.mismatchDescription = mismatchDescription;
    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        Description description  = (Description) invocation.getParameter(1);
        description.appendText(mismatchDescription);
        return null;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendText("describe mismatch as ")
                .appendValue(mismatchDescription);
    }
}
