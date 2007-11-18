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

import java.util.HashSet;
import java.util.Set;

import org.naturalcli.*;
import org.naturalcli.commands.*;


/**
 *
 * @author Ferran Busquets
 */
public class NaturalCLIDemo {
              
    public static synchronized void main(String args[])
    {
    	// example1(new String[] { "execute file " });
    }
    

    /**
     * Example 1. The fist two commands
     * 
     * @param args
     */
    public static void example1(String args[])
    {
		try {
			// Create the command
			Command showDateCommand =
				new Command(
				    "show date", 
				    "Shows the current date and time", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pd) 
		               {  System.out.println(new java.util.Date().toString());  }
		            }		
				);
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(showDateCommand);
    		// Execute
    		new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Example 2. Adding a parameter
     * 
     * @param args
     */
    public static void example2(String args[])
    {
		try {
			// Create the command
			Command showDateCommand =
				new Command(
				    "hello world <name:string>", 
				    "Says hello to the world and especially to some one.", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pd ) 
		               {  System.out.println("Hello world! And hello especially to "+pd.getParameterValue(0));  }
		            }		
				);
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(showDateCommand);
    		// Execute
    		new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Example 2. Adding a parameter
     * 
     * @param args
     */
    public static void example3(String args[])
    {
		try {
			// Create the command
			Command showDateCommand =
				new Command(
				    "hello world [<name:string>]", 
				    "Says hello to the world and, may be, especially to some one.", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pd) 
		               {  System.out.print("Hello world!");
		               	  String p0 = pd.getParameterValue(0).toString();
		                  if (p0 == null)
		                	  System.out.println();
		                  else
		                	  System.out.println(" And hello especially to "+p0);  
		                }
		            }		
				);
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(showDateCommand);
    		// Execute
    		new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
    /**
     * Example 3. The defaults
     * 
     * @param args
     */
    public static void example4(String args[])
    {    
		try {
			ParameterValidator pv = new ParameterValidator();
    		Set<Command> cs = new HashSet<Command>();
    		NaturalCLI nc = new NaturalCLI(cs, pv);
    		cs.add(new HelpCommand(cs));
    		cs.add(new HTMLHelpCommand(cs));
    		cs.add(new SleepCommand());  
    		cs.add(new ExecuteFileCommand(nc));  
			nc.execute(args, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
