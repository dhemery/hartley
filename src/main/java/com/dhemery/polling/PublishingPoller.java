package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.core.Query;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Matcher;

/**
 * A poller that publishes the result of each evaluation.
 */
public class PublishingPoller implements Poller {
    private final Publisher publisher;
    private final Poller poller;

    /**
     * Create a poller that publishes the result of each evaluation.
     */
    public PublishingPoller(Publisher publisher, Poller poller) {
        this.publisher = publisher;
        this.poller = poller;
    }

    /**
     * Poll until the condition is satisfied and publish the result of each evaluation.
     */
    @Override
    public void poll(Condition condition) {
        poller.poll(new PublishingCondition(publisher, condition));
    }

    /**
     * Poll until the subject satisfies the criteria and publish the result of each evaluation.
     */
    @Override
    public <S> void poll(S subject, Matcher<? super S> criteria) {
        poll(new MatcherCondition<S>(subject, criteria));
    }

    @Override
    public <S, V> void poll(S subject, Query<? super S, V> query, Matcher<? super V> criteria) {
        poll(new SubjectQueryProbe<S,V>(subject, query), criteria);
    }

    @Override
    public <V> void poll(Probe<? extends V> probe, Matcher<? super V> criteria) {
        poll(new ProbingCondition<V>(probe, criteria));
    }
}
