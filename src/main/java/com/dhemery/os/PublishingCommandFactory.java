package com.dhemery.os;

import com.dhemery.publishing.Publisher;

import java.util.List;
import java.util.Map;

/**
 * A command factory that wraps another factory's {@link OSCommand}s
 * in {@link PublishingCommand} wrappers.
 */
public class PublishingCommandFactory implements OSCommandFactory<RunnableCommand> {
    private final Publisher publisher;
    private final OSCommandFactory<RunnableCommand> create;

    /**
     * Create a resource factory that wraps another factory's {@link OSCommand}s
     * in {@link PublishingCommand} wrappers.
     */
    public PublishingCommandFactory(Publisher publisher, OSCommandFactory<RunnableCommand> commands) {
        this.publisher = publisher;
        create = commands;
    }

    @Override
    public RunnableCommand command(String description, String path, List<String> arguments, Map<String, String> environment) {
        RunnableCommand command = create.command(description, path, arguments, environment);
        return new PublishingCommand(publisher, command);
    }
}
