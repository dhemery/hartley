package com.dhemery.polling;

import com.dhemery.publishing.Publisher;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

import static com.dhemery.fixtures.AppendTextToDescription.appendText;
import static com.dhemery.fixtures.ConditionDissatisfiedMatcher.dissatisfaction;
import static com.dhemery.fixtures.ConditionSatisfiedMatcher.satisfaction;

public class APublishingPollEvaluator extends APollEvaluator {
    @Mock public Publisher publisher;
    private PollEvaluator evaluator;


    @Override
    protected PollEvaluator evaluator() {
        context.checking(new Expectations(){{
            ignoring(condition).describeTo(with(any(Description.class)));
            ignoring(condition).describeDissatisfactionTo(with(any(Description.class)));
            ignoring(publisher);
        }});
        return evaluator;
    }

    @Before
    public void createEvaluator() {
        evaluator = new PublishingPollEvaluator(publisher);
    }

    @Test
    public void publishesSatisfactionAsConditionSatisfiedEvent() {
        final String description = "a description";
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(true));
            allowing(condition).describeTo(with(any(Description.class))); will(appendText(description));
            oneOf(publisher).publish(with(satisfaction(description, 0)));
        }});

        evaluator.evaluate(condition);
    }

    @Test
    public void publishesDissatisfactionAsConditionDissatisfiedEvent() {
        final String description = "a description";
        final String reason = "a reason for failure";
        context.checking(new Expectations(){{
            allowing(condition).isSatisfied(); will(returnValue(false));
            allowing(condition).describeTo(with(any(Description.class))); will(appendText(description));
            allowing(condition).describeDissatisfactionTo(with(any(Description.class))); will(appendText(reason));
            oneOf(publisher).publish(with(dissatisfaction(description, reason, 1)));
        }});

        evaluator.evaluate(condition);
    }
}
