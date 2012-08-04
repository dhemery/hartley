package com.dhemery.publishing.tests;

import com.dhemery.publishing.MethodSubscription;
import com.dhemery.publishing.Subscribe;
import com.dhemery.publishing.Subscription;
import com.dhemery.publishing.fixtures.Counter;
import com.dhemery.publishing.fixtures.Publication1;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AMethodSubscription {
    @Test
    public void deliversAPublication() throws NoSuchMethodException {
        final Counter delivery = new Counter();
        Publication1 publication = new Publication1();
        Object subscriber = new Object(){
            @Subscribe
            public void receive(Publication1 publication) {
                delivery.record();
            }
        };

        Method method = subscriber.getClass().getDeclaredMethod("receive", Publication1.class);
        Subscription subscription = new MethodSubscription(subscriber, method);
        subscription.deliver(publication);

        assertThat(delivery.count, equalTo(1));
    }
}
