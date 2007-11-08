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
package org.naturalcli;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


/**
 * @author Ferran Busquets
 *
 */
public class Syntax {

	private String definition;
	private List<Token> grammar;
	private int parametersMin;
	private int parametersMax;
	
	public Syntax(String definition)
	{
		this.setDefinition(definition);
	}

	/**
	 * @param definition the definition to set
	 */
	private void setDefinition(String definition) {
		this.definition = definition;
		this.generateGrammar();
	}
		
	private void generateGrammar()
	{
		grammar = new LinkedList<Token>();
		parametersMin = 0;
		parametersMax = 0;
   		for (String s : definition.split(" "))
   		{
   			Token t = new Token(s);
   			grammar.add(t);
   			if (t.isParameter())
   			{
   				parametersMax++;
   				if (t.isOptional())
   					parametersMin++;
   			}
   		}
	}

	public boolean parse(String[] tokens, int first, ParameterValidator pv) throws UnknownParameterType
	{
		int i = first;
		for (Token tg : grammar)
		{
			String tm = tokens[i++];
			if (!tg.matches(tm, pv) && !tg.isOptional())
				return false;
		}
		return i == tokens.length;
	}


 	public Integer[] getParameterIndexes(String[] tokens, int first, ParameterValidator pv) throws UnknownParameterType
	{
 		Vector<Integer> l = new Vector<Integer>();
		int i = first;
		for (Token tg : grammar)
		{
			String tm = tokens[i++];
			if (!tg.matches(tm, pv) && !tg.isOptional())
				return null;
			else if (tg.isParameter())
				l.add(i);
		}
		return (Integer[]) l.toArray();
	}	

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}


}
