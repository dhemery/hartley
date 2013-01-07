package com.dhemery.configuring;

import com.dhemery.core.Diagnostic;

class OptionTransaction<T> {
    public final String name;
    public final T value;

    public OptionTransaction(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append(name)
                .append(":")
                .append(Diagnostic.descriptionOf(value))
                .append("]")
                .toString();
    }
}
