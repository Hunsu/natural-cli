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
	 * @throws InvalidSyntaxDefinionException 
	 * @throws InvalidTokenException 
	 */
	private void setDefinition(String definition) throws InvalidSyntaxDefinionException {
		this.definition = definition;
		this.generateGrammar();
	}
		
	/**
	 * Creates the grammar for the command
	 * @throws InvalidSyntaxDefinionException 
	 * 
	 * @throws InvalidTokenException
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
	 * @return <code>null</code> if the candidate does not match, or an array with
	 *         the index of the parameters on the candidates. If an optional
	 *         parameter is not found, then the value for the index is <code>-1</code>
	 * @throws UnknownParameterType
	 */
	public int[] parse(String[] candidates, int first, ParameterValidator pv) throws UnknownParameterType
	{
		int[] paramIndexes = new int[paramCount];
		int ip = 0;
		int ig = 0; 
		int ic = first; 
		while (ig < grammar.size() && ic < candidates.length)
		{
			Token tgrammar = grammar.get(ig); 
			boolean match = tgrammar.matches(candidates[ic], pv);
			boolean opt = tgrammar.isOptional();
			boolean param = tgrammar.isParameter();			
			/* 
			 *   comparison       increment    return
			 * match opt param   ip  ig  ic
			 *   0    0    ?      -   -   -     null
			 *   0    1    0	  0   1   0
			 *   0    1    1      1   1   0
			 *   1    ?    0      0   1   1       
			 *   1    ?    1      1   1   1
			 *   
			 */
			if (!match && !opt)
				return null;
			if (param)
				paramIndexes[ip++] = match ? ic : -1; 
			if (match)
				ic++;
			ig++;
		}
		if (ig == grammar.size() && ic == candidates.length)
			return paramIndexes;
		return null;
	}

}
