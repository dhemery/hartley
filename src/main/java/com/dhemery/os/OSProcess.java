package com.dhemery.os;

/**
 * Represents an operating system process.
 * The represented process may or may not have terminated
 * by the time this representation is created.
 */
public interface OSProcess {
    /**
     * Retrieve the first line of the output from the represented operating system process.
     * <p>
     * NOTE: There is currently no way to retrieve any output beyond the first line.
     */
    String output();
}
