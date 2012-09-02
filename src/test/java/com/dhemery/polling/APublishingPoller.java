package com.dhemery.polling;

import com.dhemery.polling.events.ConditionSatisfied;
import com.dhemery.publishing.Publisher;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

import static com.dhemery.polling.fixtures.AppendTextToDescription.appendText;
import static com.dhemery.polling.fixtures.ConditionDissatisfiedMatcher.dissatisfaction;
import static com.dhemery.polling.fixtures.ConditionDissatisfiedMatcher.dissatisfactionWithFailureCount;
import static com.dhemery.polling.fixtures.ConditionSatisfiedMatcher.satisfaction;
import static com.dhemery.polling.fixtures.ConditionSatisfiedMatcher.satisfactionWithFailureCount;

public class APublishingPoller extends PollerContract {
    @Mock public Publisher publisher;
    private Poller poller;
    public static final String DESCRIPTION = "some condition";
    public static final String REASON = "some reason";

    @Override
    protected Poller poller() {
        context.checking(new Expectations(){{
            ignoring(publisher);
        }});
        return poller;
    }

    @Before
    public void createPoller() {
        context.checking(new Expectations() {{
            allowing(condition).describeTo(with(any(Description.class))); will(appendText(DESCRIPTION));
            allowing(condition).describeDissatisfactionTo(with(any(Description.class))); will(appendText(REASON));
        }});
        poller = new PublishingPoller(publisher);
    }

    @Test
    public void publishesDissatisfaction() {
        context.checking(new Expectations(){{
            ignoring(ticker);
            allowing(publisher).publish(with(any(ConditionSatisfied.class)));
            allowing(condition).isSatisfied(); will(onConsecutiveCalls(returnValue(false), returnValue(true)));
            oneOf(publisher).publish(with(dissatisfaction(DESCRIPTION, REASON)));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void publishesSatisfaction() {
        context.checking(new Expectations(){{
            ignoring(ticker);
            allowing(condition).isSatisfied(); will(returnValue(true));
            oneOf(publisher).publish(with(satisfaction(DESCRIPTION)));
        }});

        poller.poll(ticker, condition);
    }

    @Test
    public void publishesFailureCountWithEachNotification() {
        final Sequence polling = context.sequence("polling");
        context.checking(new Expectations(){{
            ignoring(ticker);

            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(publisher).publish(with(dissatisfactionWithFailureCount(1)));

            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(publisher).publish(with(dissatisfactionWithFailureCount(2)));

            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(false));
            oneOf(publisher).publish(with(dissatisfactionWithFailureCount(3)));

            oneOf(condition).isSatisfied(); inSequence(polling); will(returnValue(true));
            oneOf(publisher).publish(with(satisfactionWithFailureCount(3)));
        }});

        poller.poll(ticker, condition);
    }
}
