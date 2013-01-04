package com.dhemery.osx;

import com.dhemery.os.Shell;

/**
 * A PlistReader that reads Plists using the {@code plutil} command.
 */
public class PlutilPlistReader implements PlistReader {
    private final Shell shell;

    /**
     * Create a PlistReader that reads Plists by running {@code plutil} in the given shell.
     */
    public PlutilPlistReader(Shell shell) {
        this.shell = shell;
    }

    @Override
    public String read(String path) {
        return shell.command("Plist Reader", "plutil")
                .withArguments("-convert", "json", "-o", "-", "--", path)
                .build().run().output();
    }
}
