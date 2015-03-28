package com.dhemery.publishing;

import com.dhemery.publishing.fixtures.Publication1;
import com.dhemery.publishing.fixtures.Subscriber;
import com.dhemery.publishing.fixtures.SubscribesTo1;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AMethodSubscription {
    @Test
    public void deliversAPublication() throws NoSuchMethodException {
        Publication1 publication = new Publication1();
        Subscriber subscriber = new SubscribesTo1("method");

        Method method = subscriber.getClass().getDeclaredMethod("method", Publication1.class);
        Subscription subscription = new MethodSubscription(subscriber, method);
        subscription.deliver(publication);

        assertThat(subscriber.deliveriesOfType(Publication1.class), is(1));
        assertThat(subscriber.deliveriesByMethod("method"), is(1));
    }
}
