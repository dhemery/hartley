package com.dhemery.configuring;

import com.dhemery.core.UnaryOperator;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

public class RequiredOptionDiagnosis implements SelfDescribing {
    private final String name;
    private final String value;
    private final UnaryOperator<String>[] operators;

    public RequiredOptionDiagnosis(String name, String value, UnaryOperator<String>[] operators) {
        this.name = name;
        this.value = value;
        this.operators = operators;
    }

    @Override
    public void describeTo(Description description) {
        String result = value;
        operation(name, result).describeTo(description);
        for(UnaryOperator<String> operator : operators) {
            result = operator.operate(result);
            description.appendText(" -> ").appendDescriptionOf(operation(operator.toString(), result));
        }
    }

    private static <T> SelfDescribing operation(final String name, final T result) {
        return new SelfDescribing() {
            @Override
            public void describeTo(Description description) {
                description
                        .appendText("[")
                        .appendText(name)
                        .appendText(":")
                        .appendValue(result)
                        .appendText("]");
            }
        };
    }

    @Override
    public String toString() {
        return StringDescription.asString(this);
    }
}
