package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

import java.util.ArrayList;
import java.util.List;

public class Journal implements SelfDescribing {
    private final List<Operation> operations = new ArrayList<Operation>();

    public void record(String operator, String result) {
        operations.add(operations(operator, result));
    }

    private Operation operations(String operator, String result) {
        return new Operation(operator, result);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("<", " -> ", ">", operations);
    }

    public String value() {
        if(operations.isEmpty()) return null;
        int last = operations.size() - 1;
        return operations.get(last).result;
    }

    private class Operation implements SelfDescribing {
        private final String operator;
        private final String result;

        public Operation(String operator, String result) {
            this.operator = operator;
            this.result = result;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendText("[")
                    .appendText(operator)
                    .appendText(":")
                    .appendValue(result)
                    .appendText("]");
        }
    }
}
