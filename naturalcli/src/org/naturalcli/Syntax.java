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

	/** Syntax definition */
	private String definition;
	
	/** List of tokens defining the grammar */
	private List<Token> grammar;
	
	/**
	 * Constructor for the Syntax class
	 * 
	 * @param definition the syntax definition
	 * @throws InvalidSyntaxDefinionException 
	 * @throws InvalidTokenException 
	 */
	public Syntax(String definition) throws InvalidSyntaxDefinionException 
	{
		this.setDefinition(definition);
	}


	/**
	 * Gets the syntax definition
	 * 
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}
	
	/**
	 * Sets the definition for the syntax
	 * 
	 * @param definition the definition to set
	 * @throws InvalidSyntaxDefinionException 
	 * @throws InvalidTokenException 
	 */
	private void setDefinition(String definition) throws InvalidSyntaxDefinionException {
		this.definition = definition;
		try {
			this.generateGrammar();
		} catch (InvalidTokenException e) {
			throw new InvalidSyntaxDefinionException(e);
		}
	}
		
	/**
	 * Creates the grammar for the command
	 * 
	 * @throws InvalidTokenException
	 */
	private void generateGrammar() throws InvalidTokenException
	{
		grammar = new LinkedList<Token>();
		String lastTypeName = "";
		boolean lastOpt = false;
   		for (String s : definition.split(" "))
   		{
   			Token t = new Token(s);
   			if (t.isParameter() && lastOpt && t.getParameterTypeName().equals(lastTypeName))
   			{
   				System.out.println("lastOpt="+lastOpt);
   				System.out.println("lasTypeName="+lastTypeName);
   				System.out.println("t.isParameter()="+t.isParameter());
   				System.out.println("t.getParameterTypeName()="+t.getParameterTypeName());
   				throw new InvalidTokenException("An optional parameter cannot be followed by a parameter of the same type.");
   			}
   			grammar.add(t);
   			if (t.isOptionalParameter())
   			{
   				lastTypeName = t.getParameterTypeName();
   				lastOpt = true;
   			} else
   				lastOpt = false;
   		}
	} 
	
	/**
	 * Parse the tokens to see if match with the syntax
	 * 
	 * @param tokens the tokens to match
	 * @param first the first item in the tokens list 
	 * @param pv the parameter validator
	 * @return <code>true</code> if the tokens matches the syntax, otherwise <code>false</code>
	 * @throws UnknownParameterType
	 */
	public boolean matches(String[] tokens, int first, ParameterValidator pv) throws UnknownParameterType
	{
		int im = first; 
		int ig = 0; 
		while (ig < grammar.size() && im < tokens.length)
		{
			boolean match = grammar.get(ig).matches(tokens[im], pv);
			boolean opt = grammar.get(ig).isOptional();
			// If !opt 			
			//		If !match => return false
			//		If match => continue
			// If opt
			//		If match => continue
			//		If !match => no increment the token index and continue 
			if (!opt && !match)
				return false;
			if (opt && !match)
				ig++;
			else
			{
				ig++;
				im++;
			}
		}
		return ig == grammar.size() && im == tokens.length;
	}

	/**
	 * Get the indexes for the tokens that are parameters
	 * 
	 * @param tokens the tokens to match
	 * @param first the first token on index
	 * @param pv the parameter validator
	 * @return Integer array with the indexes
	 * 
	 * @throws UnknownParameterType
	 */
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


}
