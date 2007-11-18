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


/**
 * Implements a class with a syntax definition and parser.
 * 
 * @author Ferran Busquets
 *
 */
public class Syntax {

	/** Syntax definition */
	private String definition;
	
	/** List of tokens defining the grammar */
	private List<Token> grammar;
	
	/** Number of parameters */
	private int paramCount;

	/**
	 * Constructor for the Syntax class
	 * 
	 * @param definition the syntax definition
	 * @throws InvalidSyntaxDefinionException 
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
	 */
	private void setDefinition(String definition) throws InvalidSyntaxDefinionException {
		if (definition == null || definition.length() == 0)
			throw new IllegalArgumentException("The definition cannot be null or empty string.");
		this.definition = definition;
		this.generateGrammar();
	}
		
	/**
	 * Creates the grammar for the command
	 * @throws InvalidSyntaxDefinionException 
	 * 
	 */
	private void generateGrammar() throws InvalidSyntaxDefinionException
	{
		grammar = new LinkedList<Token>();
		String lastTypeName = "";
		boolean lastOpt = false;
   		for (String s : definition.split(" "))
   		{
   			Token t;
			try {
				t = new Token(s);
			} catch (InvalidTokenException e) {
				throw new InvalidSyntaxDefinionException("Bad token", e);
			}
   			if (t.isParameter() && lastOpt && t.getParameterTypeName().equals(lastTypeName))
   				throw new InvalidSyntaxDefinionException("An optional parameter cannot be followed by a parameter of the same type.");
   			grammar.add(t);
   			if (t.isParameter())
   				paramCount++;
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
	 * @param candidates the candidate tokens to match
	 * @param first the first item in the tokens list 
	 * @param pv the parameter validator
	 * @return <code>null</code> if the candidate does not match, 
	 *         or a <code>ParseData</code> object
	 * @throws UnknownParameterType
	 * @see ParseResult
	 */
	public ParseResult parse(String[] candidates, int first, ParameterValidator pv) throws UnknownParameterType
	{
		if (candidates == null)
			throw new IllegalArgumentException("The string array to parse cannot be null.");
		if (pv == null)
			throw new IllegalArgumentException("Parameter validator cannot be null.");
		if (first < 0)
			throw new IllegalArgumentException("First token index have to be 0 or greater.");
		String[] paramValues = new String[paramCount];
		boolean[] tokenGiven = new boolean[this.grammar.size()];
		int ip = 0; // index for paramValues
		int it = 0; // index for tokensGiven
		int ig = 0; // index for the grammar
		int ic = first; // index for the candidates
		while (ig < grammar.size() && ic < candidates.length)
		{
			Token tgrammar = grammar.get(ig); 
			boolean match = tgrammar.matches(candidates[ic], pv);
			boolean opt = tgrammar.isOptional();
			boolean param = tgrammar.isParameter();			
			/* 
			 *          increment       
			 * match opt param   ip  it  ig  ic
			 *   0    0    ?      -   -   -   -   return null
			 *   0    1    0	  0   1   1   0    
			 *   0    1    1      1   1   1   0    
			 *   1    ?    0      0   1   1   1         
			 *   1    ?    1      1   1   1   1   
			 *   
			 */
			if (!match && !opt)
				return null;
			if (param)
				paramValues[ip++] = (opt && !match) ? null : candidates[ic]; 
			if (match)
				ic++;
			tokenGiven[it++] = match;  
			ig++;
		}
		if (ig == grammar.size() && ic == candidates.length)
			return new ParseResult(paramValues, tokenGiven);
		return null;
	}

}
