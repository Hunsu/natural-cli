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

import org.naturalcli.parameters.ParameterValidator;

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
    public NaturalCLI(Set<Command> commands) throws CommandException 
    {
    	this(commands, new ParameterValidator());
    }
    
    
    /**
     * Search a command based on the string array.
     * 
     * @param  args the arguments for the command.
     * @return the command that matches the arguments or null if no one matches.
     */
    private Command searchCommand(String[] args)
    {           
        for (Command c : this.commands)
            if (c.parse(args))
                return c;
        return null;
    }

    /**
     * Runs a command based on the arguments.
     * 
     * @param args  the string arguments to run.
     */
    public void execute(String args) throws Exception
    {
    	this.execute(args.split(" "));
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
     */
    public void execute(String[] args, int first) throws Exception
    {
        // Look for the command that matches
        Command c = this.searchCommand(args);
        // I no one matches, 
        if (c == null)
        {
            String x = "";
            for (String s : args)
                x += " "+s;
            throw new CommandException("Unknown command: "+x);
        }
        c.execute(args,1, this.pv);
    }
    
    /**
     * Runs a command based on the arguments.
     * 
     * @param args  the arguments to be parsed
     */
    public void execute(String[] args) throws Exception
    {
    	this.execute(args, 0);
    }
     

    
}
