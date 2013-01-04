package com.dhemery.osx;

/**
 * Reads a Plist into a string in JSON format.
 */
public interface PlistReader {
    /**
     * Return a JSON string representation of the Plist at the given path.
     */
    String read(String path);
}
