/* 
 * InvalidSyntaxDefinionException.java
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
 * @author Ferran Busquets
 *
 */
@SuppressWarnings("serial")
public class InvalidSyntaxDefinionException extends Exception {

	/**
	 * @param arg0
	 */
	public InvalidSyntaxDefinionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public InvalidSyntaxDefinionException(Throwable arg0) {
		super(arg0);
	}
}
