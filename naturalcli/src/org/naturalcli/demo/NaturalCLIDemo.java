/*
 * Main.java
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

package org.naturalcli.demo;

import java.util.HashSet;
import java.util.Set;

import org.naturalcli.*;
import org.naturalcli.commands.*;
import org.naturalcli.parameters.ParameterValidator;


/**
 *
 * @author Ferran Busquets
 */
public class NaturalCLIDemo {
    
              
    public static synchronized void main(String args[])
    {
		System.out.println("Demo started.");
		try {
			args = new String[] { "help" };
			ParameterValidator pv = new ParameterValidator();
    		Set<Command> cs = new HashSet<Command>();
    		cs.add(new HelpCommand(cs));
    		cs.add(new HTMLHelpCommand(cs));
    		cs.add(new SleepCommand());    		
			new NaturalCLI(cs, pv).execute(args, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Demo finished.");
    }
    
    
}
