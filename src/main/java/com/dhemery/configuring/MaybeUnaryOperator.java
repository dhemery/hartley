package com.dhemery.configuring;

import com.dhemery.core.NamedUnaryOperator;

public class MaybeUnaryOperator<T> extends NamedUnaryOperator<T> {
    public MaybeUnaryOperator(String name) {
        super(name);
    }

    @Override
    public T operate(T value) {
        return value == null ? resultForAbsent() : resultForPresent(value);
    }

    protected T resultForAbsent() { return null; };
    protected T resultForPresent(T string) { return string; };
}
