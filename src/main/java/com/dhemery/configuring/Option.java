package com.dhemery.configuring;

import com.dhemery.core.Maybe;
import org.hamcrest.SelfDescribing;

public interface Option extends SelfDescribing {
    Options source();
    String name();
    Maybe<String> value();
}
