package com.dhemery.configuring;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class Violation implements OptionFilter {
    private final Matcher<? super Option> criteria;

    public Violation(Matcher<? super Option> criteria) {
        this.criteria = criteria;
    }

    @Override
    public String valueOf(Option option) {
        Description description = new StringDescription();
        criteria.describeMismatch(option, description);
        throw new ConfigurationException(description.toString());
    }
}
