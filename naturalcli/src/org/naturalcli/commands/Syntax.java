/* 
 * CommandGrammar.java
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

import java.util.LinkedList;
import java.util.List;

import org.naturalcli.parameters.ParameterValidator;
import org.naturalcli.parameters.UnkownParameterType;

/**
 * @author Ferran Busquets
 *
 */
public class Syntax {

	private String definition;
	private List<Token> grammar;
	private int parameters;
	
	public Syntax(String definition)
	{
		this.setDefinition(definition);
	}
	
	private void generateGrammar()
	{
		grammar = new LinkedList<Token>();
		parameters = 0;
   		for (String s : definition.split(" "))
   		{
   			Token t = new Token(s);
   			grammar.add(t);
   			if (t.isParameter())
   				parameters++;
   		}
	}

	public boolean parse(String[] tokens, int first, ParameterValidator pv) throws UnkownParameterType
	{
		int i = first;
		for (Token tg : grammar)
		{
			String tm = tokens[i++];
			if (!tg.matches(tm, pv) && !tg.isOptional())
				return false;
		}
		return true;
	}

	public int[] getParameterIndexs() 
	{
		int[] result = new int[parameters];
		int result_index = 0;
		int parameter_index = 0;
		for (Token t : grammar)
		{
			if (t.isParameter())
				result[result_index++] = parameter_index;;
			parameter_index++;
		}
		return result;
	}	
	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
		this.generateGrammar();
	}
	
}
