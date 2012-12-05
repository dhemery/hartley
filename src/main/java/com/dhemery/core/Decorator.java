package com.dhemery.core;

public interface Decorator<T> {
    T decorate(T inner);
}
