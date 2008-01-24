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
		this.compile();
	}
		
	/**
	 * Creates the grammar for the command
	 * @throws InvalidSyntaxDefinionException 
	 * 
	 */
	private void compile() throws InvalidSyntaxDefinionException
	{
		grammar = new LinkedList<Token>();
		String[] tokens = definition.split(" "); 
		Token last_t = null;
   		for (int i = 0; i < tokens.length ; i++)
   		{
   			String s = tokens[i];
   			// Create the token
   			Token t;
			try {
				t = new Token(s);
			} catch (InvalidTokenException e) {
				throw new InvalidSyntaxDefinionException("Bad token.", e);
			}
			// Validating the variable argument token
			if (t.isVarArgs())
			{
				if (last_t == null || i != tokens.length-1)
					throw new InvalidSyntaxDefinionException("Variable arguments token only allowed at the end.");
				if (!last_t.isMandatoryParameter())
					throw new InvalidSyntaxDefinionException("Variable arguments have to follow a mandatory parameter.");
			}
			// Validating optional parameters
   			if (t.isParameter() && last_t != null && last_t.isOptional() && 
   			    t.getParameterTypeName().equals(last_t.getParameterTypeName()))
   			{
   				throw new InvalidSyntaxDefinionException("An optional parameter cannot be followed by a parameter of the same type.");
   			}
   			// Add the token
   			grammar.add(t);
   			// 
   			if (t.isParameter())
   				paramCount++;
   			//    			
   			last_t = t;
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
		List<Object> ParamValues = new LinkedList<Object>();
		boolean[] tokenGiven = new boolean[this.grammar.size()];
		int ip = 0; // index for paramValues
		int it = 0; // index for tokensGiven
		int ig = 0; // index for the grammar
		int ic = first; // index for the candidates
		boolean varargs = false;
		/* 
		 *                          increment       
		 * match opt param   ip  it  ig  ic
		 *   0    0    ?      -   -   -   -   return null
		 *   0    1    0      0   1   1   0    
		 *   0    1    1      1   1   1   0    
		 *   1    ?    0      0   1   1   1         
		 *   1    ?    1      1   1   1   1   
		 *   
		 */
		while (ig < grammar.size() && ic < candidates.length)
		{
			Token tgrammar = grammar.get(ig);
			// check if there are varargs
			varargs = tgrammar.isVarArgs();		
			if (varargs)
				break;
			boolean match = tgrammar.matches(candidates[ic], pv);
			boolean opt = tgrammar.isOptional();
			boolean param = tgrammar.isParameter();		
			// Validate the token
			if (!match && !opt)
				return null;
			// Increment ip and add value to paramValues
			if (param) {
				if (opt && !match)
					ParamValues.add(null);
				else
					ParamValues.add(pv.getParameterType(tgrammar.getParameterTypeName()).convertParameterValue(candidates[ic]));
				ip++;
			}
			// Increment ic if matches
			if (match)
				ic++;
			// Increment it and ig and add value to tokenGiven
			tokenGiven[it++] = match;
			ig++;
		}
		// Go for the variable arguments
		if (varargs)
		{
			tokenGiven[it] = true;
			Token token = grammar.get(ig-1);
			ig++;
			// Validate
			if (token.isOptional())
				throw new RuntimeException("Internal error: Parameter for variable arguments cannot be optional.");			
			// Go for values
			while(ic < candidates.length)
			{
				if (!token.matches(candidates[ic], pv))
					return null;
				ParamValues.add(pv.getParameterType(token.getParameterTypeName()).convertParameterValue(candidates[ic]));
				ic++;
			}
		}
		// Validate ParamValuesAux
		if (!varargs && ParamValues.size() > paramCount)
			throw new RuntimeException("Internal error: Invalid parameter count.");
		// Determine if the bucle is finished ok
		if (ic == candidates.length && ig == grammar.size())
			return new ParseResult(ParamValues.toArray(), tokenGiven);
		if (ic == candidates.length && ig == grammar.size()-1 && grammar.get(grammar.size()-1).isVarArgs())
			return new ParseResult(ParamValues.toArray(), tokenGiven);
		// Not enough values o matching error 
		return null;
	}

}
