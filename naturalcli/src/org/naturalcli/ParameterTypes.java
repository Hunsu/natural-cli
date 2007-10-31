/*
 * ParameterTypes.java
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

public class ParameterTypes {

	private IParameterType[] parameterTypes;

	/* 
     * Creates a new instance of Commands
     * @param c List of commands  
     */
    public ParameterTypes(IParameterType[] pt) 
    {   
        this.parameterTypes = pt;
    }	

    /*
     * Search a parameter type
     * @param name parameter type name 
     * @return the parameter type, if not found returns null
     */
    private IParameterType searchParameterType(String name)
    {        
        for (IParameterType pt : this.parameterTypes)
            if (pt.getParameterTypeName().equals(name))
                return pt;
        return null;
    }    
    
    /*
     * Validate a parameter value for a type
     * @param value parameter value
     * @param type parameter type name
     */
    public String validate(String value, String type) throws ParameterTypeException
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
            else {
            	IParameterType pt = this.searchParameterType(type);
            	if (pt == null)
                    return "Unknown parameter type: "+type;
            	if (!pt.validateParameter(value))
            		return pt.validationMessage(value);
            }
            return null;
        } catch (Exception ex) {
            throw new ParameterTypeException("Internal error", ex);
        }
    }
        
}
