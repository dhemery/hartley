package com.dhemery.expressing;

import com.dhemery.core.Action;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedAction;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import static com.dhemery.expressing.ImmediateExpressions.each;

public class Each<T> implements Iterable<T> {
    private final Iterable<T> all;

    public Each(Iterable<T> all) {
        this.all = all == null ? Collections.<T>emptySet() : all;
    }

    public Each<T> filter(Matcher<? super T> criteria) {
        Collection<T> accepted = new ArrayList<T>();
        each(all).forEach(performIf(criteria, appendTo(accepted)));
        return new Each(accepted);
    }

    public void forEach(Action<? super T> action) {
        if(action == null) return;
        for(T each : all) action.actOn(each);
    }

    @Override
    public Iterator<T> iterator() {
        return all.iterator();
    }

    public <F> Each<F> transform(Feature<? super T, F> feature) {
        Collection<F> features = new ArrayList<F>();
        each(all).forEach(appendFeatureTo(feature, features));
        return new Each(features);
    }

    private static <S> Action<S> appendTo(final Collection<S> results) {
        return new NamedAction<S>("append") {
            @Override
            public void actOn(S subject) {
                results.add(subject);
            }
        };
    }

    private static <S,F> Action<S> appendFeatureTo(final Feature<S, F> feature, final Collection<F> features) {
        return new NamedAction<S>("append " + feature.toString()) {
            @Override
            public void actOn(S subject) {
                features.add(feature.of(subject));
            }
        };
    }

    private static <X> Action<X> performIf(final Matcher<? super X> criteria, final Action<X> action) {
        return new NamedAction<X>(action.toString() + " if " + criteria) {
            @Override
            public void actOn(X subject) {
                if(criteria.matches(subject)) action.actOn(subject);
            }
        };
    }
}
