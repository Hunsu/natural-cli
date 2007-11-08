/* 
 * HelpCommandExecutor.java
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
package org.naturalcli.commands;


import java.io.*;

import org.naturalcli.ICommandExecutor;
import org.naturalcli.NaturalCLI;


/**
 * @author Ferran Busquets
 *
 */
public class ExecuteFileCommandExecutor implements ICommandExecutor {

	static final String COMMENT = "#";
	
	private NaturalCLI naturalCLI;
	
	public ExecuteFileCommandExecutor(NaturalCLI naturalCLI)
	{
		this.naturalCLI = naturalCLI;
	}
		
	/* (non-Javadoc)
	 * @see org.naturalcli.ICommandExecutor#execute(java.lang.Object[])
	 */
	@Override
	public void execute(Object[] params) throws Exception {
		String file_name = params[0].toString();
        BufferedReader in = new BufferedReader(new FileReader(file_name));
        String command;
        while ((command = in.readLine()) != null)
        {
        	if (command.startsWith(COMMENT))
        		continue;
    		this.naturalCLI.execute(command, 0);
        }
        in.close();
	}

}
