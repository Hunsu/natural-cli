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

import java.util.Arrays;
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
     * Example 1. The firsts commands
     * 
     * This example creates two simple commands with a very easy syntax. 
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
		          public void execute(ParseResult pr) 
		          {  System.out.println(new java.util.Date().toString());  }
		        }		
		     );
		   Command helloWorldCommand =
		     new Command(
		         "hello world", 
		         "Says hello.", 
		         new ICommandExecutor ()
		         {
		           public void execute(ParseResult pr ) 
		           {  System.out.println("Hello world!");  }
		         }		
		       );
		    // Create the set of commands
		    Set<Command> cs = new HashSet<Command>();
		    cs.add(showDateCommand);
		    cs.add(helloWorldCommand);
		    // Execute
		    new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Example 2. A command with a parameter.
     * 
     * The parameters are typed and you can define your own type also. 
     * A parameter can have a type name or a type name with a parameter name to be
     * shown when showing the help.
     * 
     * @param args
     */
    public static void example2(String args[])
    {
		try {
			// Create the command
			Command helloWorldCommand =
				new Command(
				    "hello world <name:string>", 
				    "Says hello to the world and especially to some one.", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pr) 
		               {  System.out.println("Hello world! And hello especially to "+pr.getParameterValue(0));  }
		            }		
				);
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(helloWorldCommand);
    		// Execute
    		new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Example 3. A command with an optional parameter
     * 
     * Defining optional parameters the possible interesting command lines are more interesting.
     * 
     * @param args
     */
    public static void example3(String args[])
    {
		try {
			// Create the command
			Command helloWorldCommand =
				new Command(
				    "hello world [<name:string>]", 
				    "Says hello to the world and, may be, especially to some one.", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pr) 
		               {  System.out.print("Hello world!");
		               	  String p0 = pr.getParameterValue(0).toString();
		                  if (p0 == null)
		                	  System.out.println();
		                  else
		                	  System.out.println(" And hello especially to "+p0);  
		                }
		            }		
				);
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(helloWorldCommand);
    		// Execute
    		new NaturalCLI(cs).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    

    /**
     * Example 4. An user defined type.
     * 
     * It's possible to define your own types to extend the parameter type and value checking. 
     * 
     * @param args
     */
    public static void example4(String args[])
    {
		try {
			// Create the type
			IParameterType type = 
				new IParameterType()
				{
					private final String[] dof = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

  					@Override
					public Object convertParameterValue(String strRepresentation) {
						return strRepresentation;
					}

					@Override
					public String getParameterTypeName() {
						return "dayofweek";
					}

					@Override
					public boolean validateParameter(String value) {						
						return Arrays.binarySearch(dof, value) != -1;
					}

					@Override
					public String validationMessage(String value) {
						return this.validateParameter(value) ? null : "Bad day of the week name.";
					}				
				};
			// Create the command
			Command helloWorldCommand =
				new Command(
				    "hello world on <day:dayofweek>", 
				    "Says hello to the world for that day.", 
					new ICommandExecutor ()
					{
		               public void execute(ParseResult pr) 
		               {  
		            	  System.out.println("Hello world on "+pr.getParameterValue(0));
		               }
		            }		
				);
			// Create the parameter validator
			Set<IParameterType> pts = new HashSet<IParameterType>();
			pts.add(type);
			ParameterValidator pv = new ParameterValidator(pts); 
			// Create the set of commands
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(helloWorldCommand);
    		// Execute
    		new NaturalCLI(cs, pv).execute(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
        
    
    /**
     * Example 6. Command executor extends.
     * 
     * 
     * 
     * @param args
     */
    public static void example6(String args[])
    {
		try {
			// Create the command
			@SuppressWarnings("unused")
			Command helloWorldCommand =
				new Command(
				    "hello world on <day:dayofweek>", 
				    "Says hello to the world for that day.", 
					new MyCommandExecutor(3)
					{
		               public void execute(ParseResult pr) 
		               {  
		            	  for (int i = 0 ; i < this.myvalue ; i++)
		            	    System.out.println("Hello world on "+pr.getParameterValue(0));
		               }
		            }		
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }    
    
    /**
     * Example 5. The default commands.
     * 
     * There are default commands that you can use. Help commands o be able
     * to output the command possibilities, execute a file with a list of
     * commands, ...  
     * 
     * @param args
     */
    public static void example5(String args[])
    {    
		try {
    		Set<Command> cs = new HashSet<Command>();
    		NaturalCLI nc = new NaturalCLI(cs);
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
