package com.dhemery.polling.fixtures;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;

public class AppendTextToDescription implements Action {
    private final String textToAppend;

    private AppendTextToDescription(String textToAppend) {
        this.textToAppend = textToAppend;
    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        Description description = (Description) invocation.getParameter(0);
        description.appendText(textToAppend);
        return null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("that appends text ").appendValue(textToAppend).appendText(" to the description");
    }


    public static Action appendText(String textToAppend) {
        return new AppendTextToDescription(textToAppend);
    }
}
