package org.naturalcli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {

    CommandParser parser;

    @Before
    public void setUp() throws Exception {
        Command command = new Command() {

            @Override
            public List<CommandOption> getOptions() {
                CommandOption c1 = CommandOption.builder()
                        .withLongOption("--config")
                        .withShortOption("-c")
                        .withType(OptionType.MONO)
                        .withHelp("Path to config file")
                        .build();
                CommandOption c2 = CommandOption.builder()
                        .withLongOption("--force")
                        .withShortOption("-f")
                        .withType(OptionType.VOID)
                        .build();
                CommandOption c3 = CommandOption.builder()
                        .withLongOption("--args")
                        .withShortOption("-a")
                        .withType(OptionType.UNDEFINED)
                        .build();
                CommandOption c4 = CommandOption.builder()
                        .withLongOption("--adr")
                        .withShortOption("-d")
                        .withType(OptionType.BI)
                        .build();
                List<CommandOption> list = new ArrayList<>();
                list.add(c1);
                list.add(c2);
                list.add(c3);
                list.add(c4);
                return list;
            }

            @Override
            protected void doRun() {
            }

            @Override
            public String getName() {
                return "myCommand";
            }
        };
        parser = new CommandParser(command);
    }

    @Test
    public void testValidCommands1() {
        CommandInput commandInput = parser
                .parse("--config file --force -d str1 str2 c1 c2 c3".split(" "));

        assertEquals(3, commandInput.getCommandArguments().size());
        assertEquals(3, commandInput.getCommandOptions().size());
        assertTrue(commandInput.getCommandArguments().contains("c1"));
        assertTrue(commandInput.getCommandArguments().contains("c2"));
        assertTrue(commandInput.getCommandArguments().contains("c3"));
    }

    @Test
    public void testValidCommands2() {
        CommandInput commandInput = parser
                .parse("--config file --force -a str1 str2 c1 c2 c3".split(" "));

        assertEquals(0, commandInput.getCommandArguments().size());
        assertEquals(3, commandInput.getCommandOptions().size());
    }

    @Test
    public void testInvalidCommands1() {
        try {
            parser.parse("--config file arg1 arg2 -d str1 str2".split(" "));
            fail();
        } catch (SyntaxError error) {
            assertEquals(error.getMessage(), "A non void option was found after command arguments");
        }
    }

    @Test
    public void testInvalidCommandsMissingOptionArgument() {
        try {
            parser.parse("--config -d str1 str2".split(" "));
            fail();
        } catch (SyntaxError error) {
            assertEquals(error.getMessage(), "Option --config need an argument");
        }
    }

}
