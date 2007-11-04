/*
 * Command.java
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

import java.util.HashSet;
import java.util.Set;

import org.naturalcli.parameters.ParameterValidator;

/*
 * Represents a command definition
 *
 * @author Ferran Busquets
 */
public class Command {
    
    private final char CHAR_BEGIN_PARAM = '<';
    private final char CHAR_END_PARAM = '>';
    private final char CHAR_NAME_TYPE = ':';

    private String help;
    private String[] fixed;
    private String[] pattern;
    private String[] parameterName;
    private boolean[] isParameter;
    private int paramCount;
    private ICommandExecutor executor;
    

    /**
     * Constructs a new command
     * 
     * @param syntax the syntax or the command
     * @param help the help help of the command
     * @param ce command executor
     * @throws CommandException
     */
    public Command(String syntax, String help, ICommandExecutor ce) throws CommandException {
        this.help = help;
        this.tokenize(syntax);
        this.executor = ce;
    }
   
    /**
     * Checks is the token begins with CHAR_BEGIN_PARAM and ends
     * with CHAR_END_PARAM
     *
     * @return <code>true</code> if its a parameter;
     *         <code>false</code> otherwise.
     * 
     */
    private boolean isParameterToken(String t) throws CommandException
    {
        boolean begin = t.charAt(0) == CHAR_BEGIN_PARAM;
        boolean end = t.charAt(t.length()-1) == CHAR_END_PARAM;
        if (begin && !end || !begin && end)
            throw new CommandException("Invalid parameter format");
        return begin && end;
    }

    private String removeParamBraquets(String p)
    {
        return p.substring(1, p.length()-1);
    }
    
    private String getParamName(String p) throws CommandException
    {
        int i = p.indexOf(CHAR_NAME_TYPE);
        if (i == -1)
            return p;
        else
            return p.substring(i+1);
    }    

    private String getParamType(String p) throws CommandException
    {
        int i = p.indexOf(CHAR_NAME_TYPE);
        if (i == -1)
            return p;
        else
            return p.substring(0, i);
    }    
    
    private void tokenize(String syn) throws CommandException
    {
        // Reset  attributes
        this.fixed = null;
        this.pattern = null;
        this.isParameter = null;
        this.paramCount = 0;
        // Get the tokens
        String[] tokens = syn.split(" ");
        // Look for the first parameter
        int p = 0;
        while (p < tokens.length && !this.isParameterToken(tokens[p]))
            p++; 
        // If all are fixed, fixed can point to tokens
        if (p == tokens.length)
        {
            this.fixed = tokens;
            return;
        }        
        // If we have found a parameter, fill fixed and pattern
        this.fixed = new String[p];
        this.pattern = new String[tokens.length-this.fixed.length];
        this.parameterName = new String[this.pattern.length];
        this.isParameter = new boolean[this.pattern.length];
        for (int i = 0; i < tokens.length; i++)
        {   
            if (i < this.fixed.length)
                this.fixed[i] = tokens[i];
            else {
                int j = i - this.fixed.length;
                this.isParameter[j] = this.isParameterToken(tokens[i]);
                if (this.isParameter[j])
                {
                    String param = this.removeParamBraquets(tokens[i]);
                    this.pattern[j] = this.getParamName(param);
                    this.parameterName[j] = this.getParamType(param);
                    this.paramCount++;
                }
                else
                    this.pattern[j] = tokens[i];
            }
        }

    }
    
    private boolean parseFixed(String[] ss, int first)
    {
        for (int i = 0; i < this.fixed.length; i++)
            if (!ss[i+first].equals(this.fixed[i]))
                  return false;
        return true;
    }

    private boolean parsePattern(String[] ss, int first)
    {     
        if (this.pattern == null)
            return true;
        for (int i = 0; i < this.pattern.length; i++)
        {
            if (this.isParameter[i])
               continue;
            if (!ss[this.fixed.length+i+first].equals(this.pattern[i]))
               return false;
        }
        return true;
    }
    
    public boolean parse(String ss[], int first)
    {
        return (ss.length - first) == this.length()
            && this.parseFixed(ss, first) 
            && this.parsePattern(ss, first);
    }

    public boolean parse(String ss[])
    {
        return this.parse(ss, 1);
    }

    /**
     * Determine if this is a hidden command
     * 
     * @return <code>true</> if it's a hidden command, <code>false</code> if not.
     */
    public boolean isHidden()
    {
        return this.getHelp().charAt(0) == '.';
    }
    
    
    /**
     * Get the number of strings for the command.
     * 
     * @return The number of strings for the command.
     */    
    public int length()
    {
        return this.fixed.length 
             + (this.pattern != null ? this.pattern.length : 0);
    }
    
    /**
     * Returns a string with the syntax for the commend.
     * 
     * @return A string with the syntax for the command.
     */    
    public String getSyntax()
    {
        String syn = "";
        for (String s : this.fixed)
           syn += (syn.length() == 0) ? s : " "+s;
        if (this.pattern == null)
            return syn;
        for (int i = 0 ; i < this.pattern.length ; i++)
        {           
           String s = this.isParameter[i] ? this.parameterName[i] : this.pattern[i];
           syn += " " + (this.isParameter[i] ? "<"+s+">" : s);
        }
        return syn;
    }

    /**
     * Returns the help for the commend.
     * 
     * @return The help for the command.
     */
    public String getHelp()
    {
        return this.help;
    }

    /**
     * Execute this command for that parameters.
     * 
     * @param args the arguments with the command.
     * @param first the index for the first string with the command.
     * @param pts parameter types to parse the command.
     * @throws Exception
     */
    public void execute(String args[], int first, ParameterValidator pts) throws Exception
    {
        // Obtain parameters
        String[] params = new String[this.paramCount];
        if (this.pattern != null)
        {
            int p = 0;
            for (int i = 0 ; i < this.pattern.length ; i++)
            {
                if (this.isParameter[i])
                {
//                    String m = this.validateParameter(ss[this.fixed.length+i+first], this.pattern[i]);
                    String m = pts.validate(args[this.fixed.length+i+first], this.pattern[i]);
                    if (m != null)
                        throw new CommandException("Parameter error"+": "+m);
                    params[p++] = args[first+this.fixed.length+i];
                }
            }
        }
        this.executor.execute(params);
    }
    
    public static Set<Command> createDefaultCommandSet() throws CommandException
    {
    	Set<Command> s = new HashSet<Command>();
    	s.add(Command.createHelpCommand());
    	s.add(Command.createHTMLHelpCommand());
    	return s;
    }
    
    public static Command createHelpCommand() throws CommandException
    {
    	return new Command("help", "Shows the commands help on plain text.", 
         new ICommandExecutor() {
            public void execute(Object[] params) throws Exception 
            {   
            	if (params.length == 0)
            		throw new RuntimeException("No parameters given.");
            	Command.help((Set<Command>) params[0], false);
            }
         }
    	);
    }    
    
    public static Command createHTMLHelpCommand() throws CommandException
    {
    	return new Command("htmlhelp", "Shows the commands help on html format.", 
         new ICommandExecutor() {
            public void execute(Object[] params) throws Exception 
            {   
            	if (params.length == 0)
            		throw new RuntimeException("No parameters given.");
            	Command.help((Set<Command>) params[0], true);
            }
         }
    	);
    }    
        
    /** 
     * Outputs the help for all the visible commands
     * 
     * @param html If true, outputs in html format
     */
    static private void help(Set<Command> commands, boolean html)
    {
        for (Command c : commands)
        {
            if (c.isHidden())
                continue;
            if (!html)
            {
                System.out.println(c.getSyntax());
                System.out.println("\t"+c.getHelp());
                System.out.println();
            }
            else
            {
                String syn = c.getSyntax().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                System.out.println("<b>"+syn+"</b><br>");
                System.out.println("<p>&nbsp;&nbsp;&nbsp;"+c.getHelp()+"<br>");
                System.out.println("<p>");
            }
                
        }
    }
    
}
