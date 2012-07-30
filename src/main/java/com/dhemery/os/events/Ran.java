package com.dhemery.os.events;

import com.dhemery.os.OSCommand;

/**
 * Reports that an {@link OSCommand} was executed.
 * By the time this event posts,
 * the process launched by the command
 * may or may not have terminated.
 */
public class Ran {
    private final OSCommand command;

    public Ran(OSCommand command) {
        this.command = command;
    }
    public OSCommand command() { return command; }
}
