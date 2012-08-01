package com.dhemery.polling;

public interface Probe<V> {
    V value();
    void probe();
}
