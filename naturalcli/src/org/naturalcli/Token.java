/* 
 * Token.java
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
 * <p>
 * The <code>Token</code> class implements a token for the 
 * command grammar.
 * </p>
 * 
 * @author Ferran Busquets
 */
public class Token {
	
	/** Beginning char for a parameter */
    private final char CHAR_BEGIN_PARAM = '<';
    
    /** Ending char for a parameter */
    private final char CHAR_END_PARAM = '>';

    /** Beginning char for an optional token*/
    private final char CHAR_BEGIN_OPT = '[';

    /** Ending char for an optional token */
    private final char CHAR_END_OPT= ']';
    
    /** Char separator for a parameter name and type */
    private final char CHAR_NAME_TYPE = ':';
    
    /** Texts giving sense to the token */
    private String text;
	
    /**
     * Constructor for the token
     * 
     * @param text the token text
     */
	public Token(String text)
	{
		this.text = text;
	}

	/**
	 * Checks if it's an optional token
	 * 
	 * @return <code>true</code> if its optional, <code>false</code> otherwise
	 */
    public boolean isOptional()
    {
        boolean begin = text.charAt(0) == CHAR_BEGIN_OPT;
        boolean end = text.charAt(text.length()-1) == CHAR_END_OPT;
        return begin && end;
    }
	
	/**
	 * Checks if it's a parameter token
	 * 
	 * @return <code>true</code> if it's a parameter, <code>false</code> otherwise
	 */    
    public boolean isParameter()
    {    	
        boolean begin, end;
        boolean opt = this.isOptional();
        begin = text.charAt(opt?0:1) == CHAR_BEGIN_PARAM;
        end = text.charAt(text.length()-(opt?2:1)) == CHAR_END_PARAM;
        return begin && end;
    }

	/**
	 * Gets the parameter name for the token
	 * 
	 * @return the parameter name, or <code>null</code> if it's not a parameter.
	 */        
    public String getParameterName()
    {
    	if (!this.isParameter())
    		return null;
    	String word = this.getWord();
        int i = word.indexOf(CHAR_NAME_TYPE);
        if (i == -1)
            return word;
        else
            return word.substring(i+1);
    }    

	/**
	 * Gets the parameter type name for the token
	 * 
	 * @return the parameter type name, or <code>null</code> if it's not a parameter.
	 */        
    public String getParameterTypeName()
    {
    	if (!this.isParameter())
    		return null;
    	String word = this.getWord();
        int i = word.indexOf(CHAR_NAME_TYPE);
        if (i == -1)
            return word;
        else
            return word.substring(0, i);
    }    
    
    /**
     * Determines if it's an identifier
     * 
     * @return <code>true</code> if it's an identifier, <code>false</code> otherwise
     */
    public boolean isIdentifier()
    {
    	return !this.isParameter();
    }
    
    /**
     * Obtains the word that represents the token
     * 
     * @return the word that represents the token
     */
    public String getWord()
    {
    	String word = new String(text);
    	if (this.isOptional())
    		word = text.substring(1, text.length()-1);
    	if (this.isParameter())
    		word = text.substring(1, text.length()-1);
    	return word;
    }
    
    /**
     * Checks the text given to see if it's like the token
     * 
     * @param text the text to match
     * @param pv the parameter validator
     * @return <code>true</code> if matches, <code>false</code> if not
     * @throws UnknownParameterType
     */
    public boolean matches(String text, ParameterValidator pv) throws UnknownParameterType
    {
    	if (this.isIdentifier())
    		return this.getWord().equals(text);
    	else if (this.isParameter())
    		return pv.validate(text, this.getParameterTypeName()) != null;
    	return false;
    }
}
