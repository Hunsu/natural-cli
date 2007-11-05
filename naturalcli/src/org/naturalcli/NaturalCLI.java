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

import org.naturalcli.commands.Command;
import org.naturalcli.parameters.ParameterValidator;
import org.naturalcli.parameters.UnkownParameterType;

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
     */
    public void execute(String args, int first) throws Exception
    {
    	this.execute(args.split(" "), first);
    }
    
    /**
     * Runs a command based on the arguments.
     * 
     * @param args  the arguments to be parsed
     * @param first the index on <code>args</code> of the first string for the arguments.
     * @return
     * @throws UnkownParameterType 
     */
    public boolean execute(String[] args, int first) throws Exception
    {
        // Look for the command that matches
        Command command = null;
        for (Command c : commands)
            if (c.parse(args, first, pv))
            {
                command = c;
                break;
            }
        // I no one matches, 
        if (command == null)
        	return false;
		// Obtain parameters		
		int[] indexes = command.getParametersIndexs();
		String[] params = new String[indexes.length];
		int p = 0;
		for (int i : indexes)
			params[p++] = args[first+i];
		// Execute the parameters
        command.getExecutor().execute(params);
        return true;
    }
  
    public boolean execute(String[] args) throws Exception
    {
    	return execute(args, 0);
    }
    

    
}
