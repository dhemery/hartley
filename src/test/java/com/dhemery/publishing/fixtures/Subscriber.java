package com.dhemery.publishing.fixtures;

import java.util.HashMap;
import java.util.Map;

public class Subscriber {
    private final Map<Class<?>,Integer> deliveriesOfType = new HashMap<Class<?>, Integer>();
    private final Map<String,Integer> deliveriesByMethod = new HashMap<String, Integer>();

    public int deliveriesByMethod(String methodName) {
        countDeliveriesByMethod(methodName);
        return deliveriesByMethod.get(methodName);
    }

    protected void recordDeliveryByMethod(String methodName) {
        countDeliveriesByMethod(methodName);
        deliveriesByMethod.put(methodName, deliveriesByMethod.get(methodName) + 1);
    }

    private void countDeliveriesByMethod(String methodName) {
        if(!deliveriesByMethod.containsKey(methodName)) {
            deliveriesByMethod.put(methodName, 0);
        }
    }
    public int deliveriesOfType(Class<?> type) {
        countDeliveriesOfType(type);
        return deliveriesOfType.get(type);
    }

    protected void recordDeliveryOfType(Class<?> type) {
        countDeliveriesOfType(type);
        deliveriesOfType.put(type, deliveriesOfType.get(type) + 1);
    }

    private void countDeliveriesOfType(Class<?> type) {
        if(!deliveriesOfType.containsKey(type)) {
            deliveriesOfType.put(type, 0);
        }
    }
}
