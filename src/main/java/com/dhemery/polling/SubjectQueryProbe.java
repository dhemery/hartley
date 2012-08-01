package com.dhemery.polling;

import com.dhemery.core.Query;

public class SubjectQueryProbe<S,V> implements Probe<V> {
    private final S subject;
    private final Query<? super S, V> query;
    private V value;

    public SubjectQueryProbe(S subject, Query<? super S, V> query) {
        this.subject = subject;
        this.query = query;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public void probe() {
        value = query.query(subject);
    }
}
