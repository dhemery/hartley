package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

import java.util.ArrayList;
import java.util.List;

/**
 * A journal that records a series of transformations.
 * @param <T> the type of value to record
 */
public class Journal<T> implements SelfDescribing {
    private final List<Transformation<T>> transformations = new ArrayList<Transformation<T>>();

    /**
     * Create a journal that records a series of transformations.
     * The initial value is recorded in the journal,
     * with the name of the value used as the name of the transformation.
     * @param name the name of the value being transformed
     * @param initialValue the value before transformation
     */
    public Journal(String name, T initialValue) {
        record(name, initialValue);
    }

    /**
     * Record a transformation.
     * @param name the name of the transformation
     * @param result the result of the transformation
     */
    public void record(String name, T result) {
        transformations.add(transformation(name, result));
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("<", " -> ", ">", transformations);
    }

    /**
     * Return the name of the value recorded in this journal.
     */
    public String name() {
        return transformations.get(0).name;
    }

    /**
     * Return the result of the last recorded transformation.
     */
    public T value() {
        int last = transformations.size() - 1;
        return transformations.get(last).result;
    }

    private Transformation<T> transformation(String name, T result) {
        return new Transformation(name, result);
    }

    private class Transformation<T> implements SelfDescribing {
        private final String name;
        private final T result;

        public Transformation(String name, T result) {
            this.name = name;
            this.result = result;
        }

        @Override
        public void describeTo(Description description) {
            description
                    .appendText("[")
                    .appendText(name)
                    .appendText(":")
                    .appendValue(result)
                    .appendText("]");
        }
    }
}
