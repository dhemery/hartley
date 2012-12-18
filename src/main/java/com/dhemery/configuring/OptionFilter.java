package com.dhemery.configuring;

public interface OptionFilter {
    String apply(Options source, String name);
}
