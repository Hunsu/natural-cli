/*
 * NaturalCLITest.java
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
package org.naturalcli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class NaturalCLITest {

    Set<Command> commands;
    NaturalCLI naturalCLI;
    Command command;

    @Before
    public void setUp() throws Exception {
        commands = new HashSet<Command>();
        command = mock(Command.class);
        commands.add(command);
        naturalCLI = new NaturalCLI(commands);
    }

    @Test
    public final void testExecuteWhenValidCommandIsInvoked() throws ExecutionException {
        when(command.getName()).thenReturn("marian");

        naturalCLI.execute("marian", "is", "the", "number", "1", "really pau of course margaret");

        verify(command).execute("is", "the", "number", "1", "really pau of course margaret");
    }

    @Test
    public final void testExecuteWhenInvalidCommandIsInvoked() throws ExecutionException {
        when(command.getName()).thenReturn("op");

        try {
        naturalCLI.execute("marian", "is", "the", "number", "1", "really pau of course margaret");
            fail();
        } catch (ExecutionException e) {
            String expected = "The command marian was not found";
            assertEquals(expected, e.getMessage());
        }
    }
}
