package org.naturalcli;

public class CommandOption {

    private final String longName;
    private final String shortName;
    private final String help;
    private final OptionType type;
    private String[] optionArguments;

    private CommandOption(String longName, String shortName, String help,
            OptionType type) {
        this.longName = longName;
        this.shortName = shortName;
        this.help = help;
        this.type = type;
    }

    public CommandOption(CommandOption commandOption) {
        this.longName = commandOption.longName;
        this.shortName = commandOption.shortName;
        this.help = commandOption.help;
        this.type = commandOption.type;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getHelp() {
        return help;
    }

    public OptionType getType() {
        return type;
    }

    public void setArguments(String... args) {
        if (!validArgs(args))
            throw new IllegalStateException();
        this.optionArguments = args;
    }

    public String[] getOptionArguments() {
        return optionArguments;
    }

    private boolean validArgs(String[] args) {
        switch (getType()) {
        case MONO:
            return args.length == 1;
        case BI:
            return args.length == 2;
        case UNDEFINED:
            return true;
        case VOID:
        default:
            return false;
        }
    }

    public static <T> CommandOptionBuilder builder() {
        return new CommandOptionBuilder();
    }

    @Override
    public String toString() {
        if (longName != null)
            return longName;
        return shortName;
    }

    public static class CommandOptionBuilder {
        private String longOption;
        private String shortOption;
        private String help;
        private OptionType type;

        public CommandOptionBuilder withLongOption(String longOption) {
            this.longOption = longOption;
            return this;
        }

        public CommandOptionBuilder withShortOption(String shortOption) {
            this.shortOption = shortOption;
            return this;
        }

        public CommandOptionBuilder withHelp(String help) {
            this.help = help;
            return this;
        }

        public CommandOptionBuilder withType(OptionType type) {
            this.type = type;
            return this;
        }

        public CommandOption build() {
            return new CommandOption(longOption, shortOption, help, type);
        }
    }
}
