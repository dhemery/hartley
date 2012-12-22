package com.dhemery.core;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import java.util.Collections;
import java.util.Iterator;

public abstract class Maybe<T> implements Iterable<T>, MaybePresent, SelfDescribing {
    @Override
    public String toString() {
        return StringDescription.asString(this);
    }

    public static <M> Maybe<M> definitely(M value) {
        return new Definitely<M>(value);
    }

    public static <M> Maybe<M> maybe(M value) {
        if(value == null) return absent();
        return definitely(value);
    }

    public static <M> Maybe<M> absent() {
        return new Absent<M>();
    }

    private static class Absent<M> extends Maybe<M> {
        @Override
        public Iterator<M> iterator() {
            return Collections.<M>emptyList().iterator();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public void describeTo(Description description) {
        }
    }

    private static class Definitely<M> extends Maybe<M> {
        private final M value;

        public Definitely(M value) {
            this.value = value;
        }

        @Override
        public Iterator<M> iterator() {
            return Collections.singleton(value).iterator();
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(value.toString());
        }
    }
}
