package com.dhemery.configuring;

import static com.dhemery.core.Descriptions.descriptionOf;

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
                .append(descriptionOf(value))
                .append("]")
                .toString();
    }
}
