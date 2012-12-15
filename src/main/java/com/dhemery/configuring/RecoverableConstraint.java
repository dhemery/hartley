package com.dhemery.configuring;

import org.hamcrest.Matcher;

/**
 * Returns the option value if the option satisfies some criteria.
 * Applies a recovery filter if the option does not satisfy the criteria.
 */
public class RecoverableConstraint implements OptionFilter {
    private final Matcher<? super Option> criteria;
    private final OptionFilter recovery;

    public RecoverableConstraint(Matcher<? super Option> criteria, OptionFilter recovery) {
        this.criteria = criteria;
        this.recovery = recovery;
    }

    @Override
    public String valueOf(Option option) {
        if(criteria.matches(option)) return option.value();
        return recovery.valueOf(option);
    }
}
