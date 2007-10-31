/* 
 * IParameterType.java
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

/*
 * Parameter type for all the commands
 *
 * @author Ferran Busquets
 */
public interface IParameterType {

	/*
	 * Gets the parameter type name 
	 * @return name
	 */
	String getParameterTypeName();
	
	/*
	 * Checks if a parameter value is of this type 
	 * of parameter
	 * @param value value to be validated
	 * @return true if the validation it's right
	 */
	boolean validateParameter(String value);

	/*
	 * Checks if a parameter value is of this type 
	 * of parameter and returns a detailed message
	 * if the validation fails.
	 * @param value value to be validated
	 * @return null if the validation it's right or 
	 *         a detailed message if something it's wrong
	 */
	String validationMessage(String value);
	
}
