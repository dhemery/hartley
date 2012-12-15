package com.dhemery.configuring;

import com.dhemery.configuring.filtering.WithPrefix;
import com.dhemery.configuring.filtering.WithoutPrefix;

import static com.dhemery.configuring.ConfigurationBuilder.newBuilder;
import static com.dhemery.configuring.OptionExpressions.*;
import static org.hamcrest.Matchers.not;

public class Testing {
    Configuration configuration = newBuilder().build();
    public void testing() {
        configuration.option("foo", require(defined()));
        configuration.option("foo", required());
        configuration.option("foo", require(not(nil())));
        configuration.option("foo", either(nil(), or("bar")));
        configuration.option("foo", required(), trimmed(), withPrefix("https://"));
        configuration.option("foo", required(), trimmed(), withoutPrefix("https://"));
    }

    private OptionFilter withPrefix(final String prefix) {
        return new WithPrefix(prefix);
    }

    private OptionFilter withoutPrefix(final String prefix) {
        return new WithoutPrefix(prefix);
    }

}
