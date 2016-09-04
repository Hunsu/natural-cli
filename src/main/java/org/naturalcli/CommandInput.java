package org.naturalcli;

import java.util.List;

public class CommandInput {

    private final List<String> commandArguments;
    private final List<CommandOption> commandOptions;

    public CommandInput(List<CommandOption> commandOptions, List<String> commandArguments) {
        this.commandOptions = commandOptions;
        this.commandArguments = commandArguments;
    }

    public List<String> getCommandArguments() {
        return commandArguments;
    }

    public List<CommandOption> getCommandOptions() {
        return commandOptions;
    }

    public CommandOption getOptionByShortName(String shortName) {
        for (CommandOption option : commandOptions)
            if (option.getShortName().equals(shortName))
                return option;
        return null;
    }
}
