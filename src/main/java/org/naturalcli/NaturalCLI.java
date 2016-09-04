/*
 * Options.java
 *
 * Copyright (C) 2007 Ferran Busquets
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.naturalcli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class NaturalCLI {

    private Set<Command> commands;

    public NaturalCLI(Set<Command> commands) {
        this.commands = commands;
    }

    public void execute(String... args) throws ExecutionException {
        List<String> listArgs = new ArrayList<>(Arrays.asList(args));
        boolean found = false;
        for (Command command : commands) {
            if (command.getName().equals(args[0])) {
                listArgs.remove(0);
                found = true;
                command.execute(listArgs.toArray(new String[listArgs.size()]));
            }
        }
        if (!found)
            throw new ExecutionException(String.format("The command %s was not found", args[0]));
    }
}
