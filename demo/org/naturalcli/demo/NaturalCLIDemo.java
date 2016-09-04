/*
 * Main.java
 *
 * Copyright (C) 2007 Ferran Busquets
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.naturalcli.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.naturalcli.Command;
import org.naturalcli.CommandOption;
import org.naturalcli.ExecutionException;
import org.naturalcli.NaturalCLI;
import org.naturalcli.OptionType;
import org.naturalcli.demo.date.DateType;

public class NaturalCLIDemo {

    /*
     * Example: myprogram convert --from unix --to gregorian 1473002975 Result:
     * 1473002975 (unix) is 2016-09-04 in gregorian calendar
     */
    public static void main(String[] args) {
        Set<Command> commands = new HashSet<>();
        commands.add(new DateUtils());
        NaturalCLI cli = new NaturalCLI(commands);
        try {
            cli.execute(args);
        } catch (ExecutionException e) {
        }
    }

    private static class DateUtils extends Command {

        @Override
        protected void doRun() {
            String from = getCommandInput().getCommandOptions().get(0).getOptionArguments()[0];
            String to = getCommandInput().getCommandOptions().get(1).getOptionArguments()[0];
            List<String> args = getCommandInput().getCommandArguments();
            for (String arg : args) {
                String converted = convert(Long.valueOf(arg), DateType.from(from),
                        DateType.from(to));
                System.out.println(
                        String.format("%s (%s) is %s in %s calendar", arg, from, converted, to));
            }
        }

        private String convert(long arg, DateType from, DateType to) {
            if (DateType.UNIX.equals(from) && DateType.GREGORIAN.equals(to)) {
                Calendar date = Calendar.getInstance();
                date.setTimeInMillis(arg * 1000);
                return new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
            }
            throw new IllegalArgumentException();
        }

        @Override
        public List<CommandOption> getOptions() {
            CommandOption from = CommandOption.builder()
                    .withLongOption("from")
                    .withShortOption("f")
                    .withType(OptionType.MONO)
                    .build();
            CommandOption to = CommandOption.builder()
                    .withLongOption("to")
                    .withShortOption("t")
                    .withType(OptionType.MONO)
                    .build();
            List<CommandOption> options = new ArrayList<>();
            options.add(from);
            options.add(to);
            return options;
        }

        @Override
        public String getName() {
            return "convert";
        }

    }
}
