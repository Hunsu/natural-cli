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

/**
 * Encapsulates the commands to know and to execute
 * @author Ferran Busquets
 */
public class Commands {
    

    private Command[] commands;
    private ParameterTypes pts;
    
    /* 
     * Creates a new instance of Commands
     * 
     * @param c List of commands  
     */
    public Commands(Command[] c, ParameterTypes pts) throws CommandException 
    {   
        this.commands = c;
        this.pts = pts;
    }

    /* 
     * Creates a new instance of Commands
     * 
     * @param c List of commands  
     */
    public Commands(Command[] c) throws CommandException 
    {   
        this.commands = c;
        this.pts = new ParameterTypes();
    }
    
    /* 
     * Outputs the help for all the visible commands
     * 
     * @param html If true, outputs in html format
     */
    private void help(boolean html)
    {
        for (Command c : this.commands)
        {
            if (c.getDescription().charAt(0) == '.')
                continue;
            if (!html)
            {
                System.out.println(c.getSyntax());
                System.out.println("\t"+c.getDescription());
                System.out.println();
            }
            else
            {
                String syn = c.getSyntax().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                System.out.println("<b>"+syn+"</b><br>");
                System.out.println("<p>&nbsp;&nbsp;&nbsp;"+c.getDescription()+"<br>");
                System.out.println("<p>");
            }
                
        }
    }
    
    /*
     * Search a command based on the string array
     * 
     * @param args arguments for the command
     * @return the command that matches the arguments or null if no one matches
     */
    private Command searchCommand(String[] args)
    {        
        for (Command c : this.commands)
            if (c.parse(args))
                return c;
        return null;
    }
    
    /*
     * Runs a command based on the arguments
     * 
     * @param args arguments to run
     * @param first index on <code>args</code> of the first string for the arguments
     */
    public void execute(String[] args, int first) throws Exception
    {
    	// Check if the command is some of the predefined
        if (args[first].equals("help")) {
            this.help(false);
            return;
        } else if (args[first].equals("htmlhelp")) {
            this.help(true);
            return;
        }
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
        c.execute(args,1, this.pts);
    }
    
}
