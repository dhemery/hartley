package com.dhemery.configuring;

import org.hamcrest.SelfDescribing;

public interface Option extends SelfDescribing {
    Options source();
    String name();
}
