package com.dhemery.expressing;

import org.hamcrest.Matcher;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator that supplies those items from a source iterator
 * that satisfy some criteria.
 * @param <T> the type of object supplied by the source iterator
 */
class FilteringIterator<T> implements Iterator<T> {
    private final Iterator<T> source;
    private final Matcher<? super T> criteria;
    private T next;

    /**
     * Create an iterator that supplies those items from the source iterator that satisfy the given criteria.
     */
    public FilteringIterator(Iterator<T> source, Matcher<? super T> criteria) {
        this.source = source;
        this.criteria = criteria;
        next = nextMatchingItem();
    }

    /**
     * Return whether the source iterator has a next item that satisfies
     * this iterator's criteria.
     */
    @Override
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Return the next item from the source iterator that satisfied
     * this iterator's criteria.
     */
    @Override
    public T next() {
        if(!hasNext()) throw new NoSuchElementException();
        T current = next;
        next = nextMatchingItem();
        return current;
    }

    @Override
    public void remove() {
        source.remove();
    }

    private T nextMatchingItem() {
        while(source.hasNext()) {
            T candidate = source.next();
            if(criteria.matches(candidate)) return candidate;
        }
        return null;
    }
}
