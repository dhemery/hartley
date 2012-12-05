package com.dhemery.expressing;

import com.dhemery.core.*;
import com.dhemery.polling.Ticker;

/**
 * An {@link Expressive} that always uses the same ticker builder and condition decorator.
 * The {@link #tickerBuilder()} method always returns the same ticker builder.
 * The {@link #conditionDecorator()} method always returns the same condition decorator.
 */
public class FixedExpressive extends Expressive {
    private final Decorator<Condition> conditionDecorator;
    private final Builder<Ticker> tickerBuilder;

    /**
     * Initialize {@code FixedExpressive} to use the given ticker builder and condition decorator.
     */
    public FixedExpressive(Builder<Ticker> tickerBuilder, Decorator<Condition> conditionDecorator) {
        this.tickerBuilder = tickerBuilder;
        this.conditionDecorator = conditionDecorator;
    }

    /**
     * Initialize {@code FixedExpressive} with a ticker builder and condition decorator
     * obtained from another {@code Expressive}.
     */
    public FixedExpressive(Expressive source) {
        this(source.tickerBuilder(), source.conditionDecorator());
    }

    /**
     * Initialize {@code FixedExpressive} to use the given ticker builder
     * and a condition decorator that adds no decoration.
     */
    public FixedExpressive(Builder<Ticker> tickerBuilder) {
        this(tickerBuilder, identityWrapper());
    }

    /**
     * Return the ticker builder supplied at construction.
     */
    @Override
    public Builder<Ticker> tickerBuilder() {
        return tickerBuilder;
    }

    /**
     * Return the condition decorator supplied at construction.
     */
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
