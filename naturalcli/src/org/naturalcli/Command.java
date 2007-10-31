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

import java.util.regex.Pattern;

/*
 * Represents a command on a command line
 *
 * @author Ferran Busquets
 */
public class Command {
    
    private final char CHAR_BEGIN_PARAM = '<';
    private final char CHAR_END_PARAM = '>';
    private final char CHAR_NAME_TYPE = ':';

    private String description;
    private String[] fixed;
    private String[] pattern;
    private String[] parameterName;
    private boolean[] isParameter;
    private int paramCount;
    private ICommandExecutor executor;
    
    
    /*
     * Creates a new instance of Command 
     * 
     */
    public Command(String syn, String desc, ICommandExecutor ce) throws CommandException {
        this.description = desc;
        this.tokenize(syn);
        this.executor = ce;
    }
   
    /*
     * Checks is the token begins with CHAR_BEGIN_PARAM and ends
     * with CHAR_END_PARAM
     *
     * @return true if its a parameter
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

    public int length()
    {
        return this.fixed.length 
             + (this.pattern != null ? this.pattern.length : 0);
    }
    
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

    public String getDescription()
    {
        return this.description;
    }

  /*  
    private String validateParameter(String value, String type) throws CommandException
    {
        try {
            if (type.equals("identifier"))
            {
                if (!Pattern.matches("\\w+", value))
                    return "Bad identifier";
            } else if (type.equals("number"))
            {
                if (!Pattern.matches("\\d+", value))
                    return "Bad number";
            } else if (type.equals("email")) {
                if (!Pattern.matches("(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*)", value))
                    return "Invalid email: "+value;
            } else if (type.equals("parameter")) {
                if (!Pattern.matches("[\\.\\w]+", value))
                    return "Invalid parameter name: "+value;
            } else if (type.equals("string"))            
                return null;
            else
              return "Unknown parameter type: "+type;
            return null;
        } catch (Exception ex) {
            throw new CommandException("Internal error", ex);
        }
    } */
    
    public void execute(String ss[], int first, ParameterTypes pts) throws Exception
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
                    String m = pts.validate(ss[this.fixed.length+i+first], this.pattern[i]);
                    if (m != null)
                        throw new CommandException("Parameter error"+": "+m);
                    params[p++] = ss[first+this.fixed.length+i];
                }
            }
        }
        this.executor.execute(params);
    }
}
