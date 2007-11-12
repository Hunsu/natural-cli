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

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.naturalcli.commands.*;

/**
 * @author Ferran Busquets
 *
 */
public class NaturalCLITest {

	Set<Command> commands;
	NaturalCLI naturalCLI;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
		commands = new HashSet<Command>();
		naturalCLI = new NaturalCLI(commands);
		commands.add(new HelpCommand(commands));
		commands.add(new HTMLHelpCommand(commands));
		commands.add(new SleepCommand());  
		commands.add(new ExecuteFileCommand(naturalCLI));  	
		commands.add(        
			new Command(
            "marian is the number <integer> really [<string>] of course <string>", 
            "Just a test",
            new ICommandExecutor() {
               public void execute(Object[] params) 
               {
            	   System.out.println("Number:"+params[0]);
            	   System.out.println("String1:"+params[1]);
            	   System.out.println("String2:"+params[2]);
               }
            }
            )
        );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.naturalcli.NaturalCLI#execute(java.lang.String, int)}.
	 */
	@Test
	public final void testExecute() throws ExecutionException {
		naturalCLI.execute("marian is the number 1 really pau of course margaret");
		naturalCLI.execute("marian is the number 1 really of course margaret");
	}

}
