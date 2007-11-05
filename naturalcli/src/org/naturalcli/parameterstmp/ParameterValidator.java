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

package org.naturalcli.parameterstmp;

import java.util.Collection;



/**
 * This class checks parameters values against their types.
 *
 * @author Ferran Busquets
 *
 * @see IParameterType
 */
public class ParameterValidator {

	private Collection<IParameterType> parameterTypes;
	
	/** 
     * Creates a new instance of <code>ParameterValidator</code> with default parameter types
     * 
     */
    public ParameterValidator() 
    {   
    	this.parameterTypes = DefaultParameterTypes.createSet();
    }	
	
	/** 
     * Creates a new instance of <code>ParameterValidator</code>
     * 
     * @param parameterTypes the parameter types collection
     */
    public ParameterValidator(Collection<IParameterType> parameterTypes) 
    {   
    	this.parameterTypes = parameterTypes;
    }	


    /**
     * Validate a parameter value for a type
     * 
     * @param value the parameter value
     * @param type the parameter type name
     * @return <code>null</code> if validated, otherwise a error message
     * @throws UnkownParameterType raised if the parameter is not found
     */
    public String validate(String value, String type) throws UnkownParameterType
    {
    	IParameterType pt = null;
    	// Look for the parameter type
    	for (IParameterType s : this.parameterTypes)
    	{
    		if (s.getParameterTypeName().equals(type))
    		{
    			pt = s;
    			break;
    		}
    	}
    	// If not found throw exception
    	if (pt == null)
            throw new UnkownParameterType(type);
    	// Validate the parameter
    	return pt.validationMessage(value);
    }
        
}
