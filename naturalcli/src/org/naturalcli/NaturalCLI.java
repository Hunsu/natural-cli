/*
 * Options.java
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

import java.util.Set;



/**
 * A set of commands understood by the CLI
 * 
 * @author Ferran Busquets
 */
public class NaturalCLI {
    
    private Set<Command> commands;
	
    private ParameterValidator pv;
    
    public NaturalCLI(Set<Command> commands, ParameterValidator pv)    
    {
    	this.commands = commands;
    	this.pv = pv;
    }
    
    /** 
     * Creates a new instance of Commands
     * 
     * @param c List of commands  
     */
    public NaturalCLI(Set<Command> commands) 
    {
    	this(commands, new ParameterValidator());
    }
    
    /**
     * Runs a command based on the arguments.
     * 
     * @param args  the string arguments to run.
     * @param first the index on <code>args</code> of the first string for the arguments.
     * @throws ExecutionException 
     * @throws UnknownParameterType 
     */
    public void execute(String args, int first) throws ExecutionException
    {
    	execute(args.split(" "), first);
    }
    
    /**
     * Runs a command based on the arguments.
     * 
     * @param args  the arguments to be parsed
     * @param first the index on <code>args</code> of the first string for the arguments.
     * @throws ExecutionException 
     */
    public void execute(String[] args, int first) throws ExecutionException 
    {
    	if (args == null || args.length == 0)
    		throw new ExecutionException("Nothing to execute.");
    	// Look for the command that the parse works
    	int[] iparameters = null;
        Command command = null;
        for (Command c : commands)
			try {
				iparameters = c.getSyntax().parse(args, first, pv);
				if (iparameters != null)
				{
				    command = c;
				    break;
				}
			} catch (UnknownParameterType e) {
				throw new ExecutionException("Unknown parameter type when matching command.", e);
			}
        if (command == null)
        	throw new ExecutionException("No command matches.");
        if (iparameters == null)
        	throw new NullPointerException("Null parameters list.");
		// Obtain parameters		
		String[] params = new String[iparameters.length];
		int p = 0;
		for (int i : iparameters)
			params[p++] = (i == -1) ? null : args[first+i];
		// Execute the command
        command.getExecutor().execute(params);
    }
    
    public void execute(String[] args) throws ExecutionException
    {
    	execute(args, 0);
    }
    
    public void execute(String args) throws ExecutionException
    {
    	execute(args, 0);
    }
     
    
    
}
