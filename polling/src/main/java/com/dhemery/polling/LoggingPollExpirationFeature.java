package com.dhemery.polling;

import com.dhemery.core.Condition;
import com.dhemery.core.Feature;
import com.dhemery.core.NamedFeature;


public class LoggingPollExpirationFeature extends NamedFeature<Condition, Boolean> {
    public LoggingPollExpirationFeature(String name) {
        super(name);
    }

    @Override
    public Boolean of(Condition subject) {
        return null;
    }
}
