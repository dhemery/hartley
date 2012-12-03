package com.dhemery.polling;

import com.dhemery.core.NamedAction;

/**
 * An action that ticks a ticker.
 */
public class TickAction extends NamedAction<Ticker> {

    /**
     * Create an action that ticks a ticker.
     */
    public TickAction() {
        super("tick");
    }

    @Override
    public void actOn(Ticker ticker) {
        ticker.tick();
    }

    /**
     * Create an action that ticks a ticker.
     */
    public static TickAction tick() {
        return new TickAction();
    }
}
