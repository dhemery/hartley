package com.dhemery.polling;

import com.dhemery.core.Query;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.hamcrest.internal.SelfDescribingValue;

public class SubjectQuerySampler<S, V> extends Sampler<V> {
    private final S subject;
    private final Query<? super S, V> query;

    public SubjectQuerySampler(S subject, Query<? super S, V> query) {
        this.subject = subject;
        this.query = query;
    }

    @Override
    protected V takeSample() {
        return query.query(subject);
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(selfDescribing(subject)).appendText(" ").appendDescriptionOf(query);
    }

    private SelfDescribing selfDescribing(S subject) {
        if(subject instanceof SelfDescribing) return (SelfDescribing) subject;
        return new SelfDescribingValue<S>(subject);
    }
}
