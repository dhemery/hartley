package com.dhemery.publishing;

import com.dhemery.fixtures.Publication1;
import com.dhemery.fixtures.Subscriber;
import com.dhemery.fixtures.SubscribesTo1;
import com.dhemery.fixtures.SubscribesTo1Twice;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AMethodSubscriptionChannel {
    private Channel channel;

    @Before
    public void setUp() {
        channel = new MethodSubscriptionChannel();
    }

    @Test
    public void publishesToASubscriber() {
        Publication1 publication = new Publication1();
        Subscriber subscribesTo1 = new SubscribesTo1("method1");

        channel.subscribe(subscribesTo1);
        channel.publish(publication);

        assertThat(subscribesTo1.deliveriesOfType(publication.getClass()), is(1));
        assertThat(subscribesTo1.deliveriesByMethod("method1"), is(1));
    }

    @Test
    public void publishesToAMultiplySubscribedSubscriber() {
        Publication1 publication1 = new Publication1();
        Subscriber subscribesTo1Twice = new SubscribesTo1Twice("method1", "method2");

        channel.subscribe(subscribesTo1Twice);
        channel.publish(publication1);

        assertThat(subscribesTo1Twice.deliveriesByMethod("method1"), is(1));
        assertThat(subscribesTo1Twice.deliveriesByMethod("method2"), is(1));
        assertThat(subscribesTo1Twice.deliveriesOfType(publication1.getClass()), is(2));
    }

    @Test
    public void publishesToMultipleSubscribersOfASinglePublication() {
        Publication1 publication1 = new Publication1();
        Subscriber subscriber1 = new SubscribesTo1("method of subscriber 1");
        Subscriber subscriber2 = new SubscribesTo1("method of subscriber 2");

        channel.subscribe(subscriber1);
        channel.subscribe(subscriber2);
        channel.publish(publication1);

        assertThat(subscriber1.deliveriesOfType(publication1.getClass()), is(1));
        assertThat(subscriber1.deliveriesByMethod("method of subscriber 1"), is(1));
        assertThat(subscriber2.deliveriesOfType(publication1.getClass()), is(1));
        assertThat(subscriber2.deliveriesByMethod("method of subscriber 2"), is(1));
    }
}
