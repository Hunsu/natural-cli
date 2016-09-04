package org.naturalcli;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

public class CommandParser {

    private final Command command;

    public CommandParser(Command command) {
        this.command = command;
    }

    public CommandInput parse(String[] args) {
        ListIterator<String> iterator = Arrays.asList(args).listIterator();
        List<String> commandArguments = new ArrayList<>();
        List<CommandOption> commandOptions = new ArrayList<>();
        while (iterator.hasNext()) {
            String arg = iterator.next();
            if (isValidOption(arg)) {
                CommandOption option = getArgumentFromName(arg);
                commandOptions.add(option);
                Queue<String> arguments = getArguments(iterator);
                if (!option.getType().equals(OptionType.VOID) && !commandArguments.isEmpty()) {
                    throw new SyntaxError("A non void option was found after command arguments");
                }
                handleOptionArguments(option, arguments);
                commandArguments.addAll(arguments);
            } else {
                commandArguments.add(arg);
            }
        }
        return new CommandInput(commandOptions, commandArguments);
    }

    private void handleOptionArguments(CommandOption option, Queue<String> arguments) {
        switch (option.getType()) {
        case MONO:
            if (arguments.isEmpty())
                throw new SyntaxError(String.format("Option %s need an argument", option));
            option.setArguments(arguments.poll());
            break;
        case BI:
            if (arguments.size() < 2)
                throw new SyntaxError(String.format("Option %s need two arguments", option));
            option.setArguments(arguments.poll(), arguments.poll());
            break;
        case UNDEFINED:
            handleOptionArgumentForUndefinedType(option, arguments);
            break;
        case VOID:
        default:
            break;
        }

    }

    private void handleOptionArgumentForUndefinedType(CommandOption option,
            Queue<String> arguments) {
        if (arguments.size() < 3)
            throw new SyntaxError(String.format(
                    "Option %s need more than two arguments or the option is mal defined", option));
        option.setArguments(arguments.toArray(new String[arguments.size()]));
        arguments.clear();
    }

    protected CommandOption getArgumentFromName(String option) {
        for (CommandOption commandOption : command.getOptions())
            if (isValidLongOption(option) && option.endsWith(commandOption.getLongName())
                    || isValidShoprtOption(option)
                            && option.endsWith(commandOption.getShortName()))
                return new CommandOption(commandOption);
        throw new IllegalStateException(String.format("Option %s is undefined", option));
    }

    private Queue<String> getArguments(ListIterator<String> iterator) {
        Queue<String> args = new ArrayDeque<>();
        while (iterator.hasNext()) {
            String candidate = iterator.next();
            if (!isValidOption(candidate))
                args.add(candidate);
            else {
                iterator.previous();
                break;
            }
        }
        return args;
    }

    private boolean isValidOption(String candidate) {
        return isValidShoprtOption(candidate) || isValidLongOption(candidate);
    }

    private boolean isValidShoprtOption(String candidate) {
        return candidate.startsWith("-") && candidate.charAt(1) != '-';
    }

    private boolean isValidLongOption(String candidate) {
        return candidate.startsWith("--");
    }
}
