/*
 * Command.java
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

import java.util.List;

public abstract class Command {
    private final CommandParser parser;
    private CommandInput commandInput;

    public Command() {
        this.parser = new CommandParser(this);
    }

    public Command(CommandParser parser) {
        this.parser = parser;
    }

    public abstract String getName();

    public void execute(String... args) {
        parse(args);
        doRun();
    }

    protected abstract void doRun();

    protected void parse(String... args) {
        commandInput = parser.parse(args);
    }

    protected CommandInput getCommandInput() {
        return commandInput;
    }

    public abstract List<CommandOption> getOptions();
}
