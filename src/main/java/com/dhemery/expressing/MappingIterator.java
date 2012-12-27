package com.dhemery.expressing;

import com.dhemery.core.Feature;

import java.util.Iterator;

/**
 * An iterator that supplies a function of the items supplied by another iterator.
 * @param <T> the type of object supplied by the source iterator
 * @param <F> the type of value supplied by the function
 */
class MappingIterator<T, F> implements Iterator<F> {
    private final Iterator<T> source;
    private final Feature<? super T, F> function;

    /**
     * Create an iterator that supplies the value returned by the given function
     * for each item supplied by the given iterator iterator.
     */
    public MappingIterator(Iterator<T> source, Feature<? super T, F> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    /**
     * Return the result of applying the function to the next item supplied by the source iterator.
     */
    @Override
    public F next() {
        return function.of(source.next());
    }

    @Override public void remove() {
        source.remove();
    }
}
