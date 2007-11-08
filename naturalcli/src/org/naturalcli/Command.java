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


/*
 * Represents a command definition
 * 
 * @author Ferran Busquets
 */
public class Command {

	private String help;

	private ICommandExecutor executor;
	private Syntax syntax;

	/**
	 * Constructs a new command
	 * 
	 * @param syntax
	 *            the syntax or the command
	 * @param help
	 *            the help help of the command
	 * @param ce
	 *            command executor
	 */
	public Command(String syntax, String help, ICommandExecutor ce) {
		this.help = help;
		this.syntax = new Syntax(syntax);
		this.executor = ce;
	}

	/**
	 * Determine if this is a hidden command
	 * 
	 * @return <code>true</code> if it's a hidden command, <code>false</code> if not.
	 */
	public boolean isHidden() {
		return this.getHelp().charAt(0) == '.';
	}

	/**
	 * Returns a string with the syntax for the commend.
	 * 
	 * @return A string with the syntax for the command.
	 */
	public Syntax getSyntax() {
		return this.syntax;
	}

	/**
	 * Returns the help for the commend.
	 * 
	 * @return The help for the command.
	 */
	public String getHelp() {
		return this.help;
	}

	/**
	 * @return the executor
	 */
	public ICommandExecutor getExecutor() {
		return executor;
	}

	public void execute(String args[], int first, ParameterValidator pv) throws Exception
	{
		// Parse
		if (!this.syntax.parse(args, first, pv))
			throw new RuntimeException("Cannot parse arguments."); 
	}


}
