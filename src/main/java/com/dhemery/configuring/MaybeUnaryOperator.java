package com.dhemery.configuring;

import com.dhemery.core.Maybe;
import com.dhemery.core.NamedUnaryOperator;

public class MaybeUnaryOperator<T> extends NamedUnaryOperator<Maybe<T>> {
    public MaybeUnaryOperator(String name) {
        super(name);
    }

    @Override
    public Maybe<T> operate(Maybe<T> maybe) {
        for(T value : maybe) return Maybe.maybe(resultForPresent(value));
        return Maybe.maybe(resultForAbsent());
    }

    protected T resultForAbsent() { return null; };
    protected T resultForPresent(T string) { return string; };
}
