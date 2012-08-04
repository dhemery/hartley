package com.dhemery.publishing.fixtures;

import com.dhemery.publishing.Subscribe;

public class SubscribesTo1Twice extends Subscriber {
    private final String method1Tag;
    private final String method2Tag;

    public SubscribesTo1Twice(String method1Tag, String method2Tag) {
        this.method1Tag = method1Tag;
        this.method2Tag = method2Tag;
    }
    @Subscribe public void method1(Publication1 publication) {
        recordDeliveryOfType(publication.getClass());
        recordDeliveryByMethod(method1Tag);
    }

    @Subscribe public void method2(Publication1 publication) {
        recordDeliveryOfType(publication.getClass());
        recordDeliveryByMethod(method2Tag);
    }
}
