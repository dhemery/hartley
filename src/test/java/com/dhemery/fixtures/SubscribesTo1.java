package com.dhemery.fixtures;

import com.dhemery.publishing.Subscribe;

public class SubscribesTo1 extends Subscriber {
    private final String methodTag;

    public SubscribesTo1(String methodTag) {
        this.methodTag = methodTag;
    }

    @Subscribe public void method(Publication1 publication) {
        recordDeliveryOfType(publication.getClass());
        recordDeliveryByMethod(methodTag);
    }
}
