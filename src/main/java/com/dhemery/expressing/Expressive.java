package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.Ticker;

/**
 * Expressive methods to make assertions, wait for conditions,
 * and establish preconditions before taking an action.
 */
public class Expressive extends AbstractExpressive {
    private final Decorator<Condition> conditionDecorator;
    private final Builder<Ticker> tickerBuilder;

    /**
     * Create an {@code Expressive} that uses the given ticker builder and condition decorator.
     */
    public Expressive(Builder<Ticker> tickerBuilder, Decorator<Condition> conditionDecorator) {
        this.tickerBuilder = tickerBuilder;
        this.conditionDecorator = conditionDecorator;
    }

    /**
     * Create an {@code Expressive} that gets its ticker builder and condition decorator
     * from the given source.
     */
    public Expressive(Expressive source) {
        this(source.tickerBuilder(), source.conditionDecorator());
    }

    /**
     * Create an {@code Expressive} that uses the given ticker builder,
     * and does not decorate conditions before polling.
     */
    public Expressive(Builder<Ticker> tickerBuilder) {
        this(tickerBuilder, identityWrapper());
    }

    @Override
    public Builder<Ticker> tickerBuilder() {
        return tickerBuilder;
    }

    @Override
    public Decorator<Condition> conditionDecorator() {
        return conditionDecorator;
    }

    private static Decorator<Condition> identityWrapper() {
        return new Decorator<Condition>() {
            @Override
            public Condition decorate(Condition inner) {
                return inner;
            }
        };
    }
}
