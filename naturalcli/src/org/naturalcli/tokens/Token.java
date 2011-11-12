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
package org.naturalcli.tokens;

import java.util.regex.Pattern;

import org.naturalcli.InvalidTokenException;

/**
 * @author Ferran Busquets
 *
 */
abstract public class Token {

    private Token preceding = null;
    
    private Token following = null;
    
    /** Texts giving sense to the token */
    private String text;    

    /** Texts giving sense to the token */
    private String text_without_optional;    
    
    /** If the token is optional */
    private boolean optional;

    /**
     * Constructor for the token
     * 
     * @param text the token text
     * @throws InvalidTokenException 
     */
    public Token(String text) throws InvalidTokenException {
        this.setText(text);
    }    
        
    /**
     * Get the token text 
     * 
     * @param without_optional
     * @return the token text 
     */
    public String getText() {
        return text;
    }  
    
    /**
     * Set the token text and validate it
     * @param text the token text to set
     * @throws InvalidTokenException 
     */
    public void setText(String text) throws InvalidTokenException {
        this.text = text;
        // Determine if it is optional
        optional = Pattern.matches("^\\[.*\\]$", text);
        if (Pattern.matches("^\\[.*$", text))
            throw new InvalidTokenException("Bad optional token: missing ']' char at end.");
        if (Pattern.matches("^.*\\]$", text)) 
            throw new InvalidTokenException("Bad optional token: missing '[' char at begin.");
        // Fill text_without_optional
        if (this.isOptional())
            text_without_optional = text.substring(1, text.length()-2);
        else 
            text_without_optional = text;
    }   
    
    
    /**
     * Sets the preceding token and runs validations. 
     * 
     * @param t the preceding token
     */
    public void setPreceding(Token t) throws InvalidTokenException
    {
        this.preceding = t;
        this.validatePreceding(this.preceding);
    }

    /**
     * Sets the following token and runs validations
     * 
     * @param t the following token
     */
    public void setFollowing(Token t) throws InvalidTokenException
    {
        this.following = t;
        this.validateFollowing(this.following);
    }
        
    /**
     * @return the text_without_optional
     */
    protected String getTextWithoutOptional() {
        return text_without_optional;
    }

    /**
     * Checks if it's an optional token
     * 
     * @return <code>true</code> if its optional, <code>false</code> otherwise
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * 
     * @return
     */
    public boolean isMandatory() {
        return !isOptional();
    }
    
    /**
     * Determines if the string matches the token 
     * 
     * @param text the text to match
     * @return <code>true</code> if its optional, <code>false</code> otherwise
     */
    abstract public boolean matches(String text);
    
    /**
     * 
     * @return
     */
    abstract public ITokenDefinition getDefinition();

    /**
     * Determines if the preceding is compatible with this 
     * 
     * @param t the preceding token
     * @return <code>null</code> if its compatible or the error messag
     *         about the incompatibility
     */
    @SuppressWarnings("unused")
    protected void validatePreceding(Token t) throws InvalidTokenException
    {
    }

    /**
     * Determines if the following  is compatible with this 
     * 
     * @param t the folling token
     * @return <code>null</code> if its compatible or the error messag
     *         about the incompatibility
     */
    @SuppressWarnings("unused")
    protected void validateFollowing(Token t) throws InvalidTokenException 
    {
    }

    /**
     * If it's possible to give more than one values of this kind 
     * 
     * @return <code>true</code> if its a multivalue or <code>false</code> otherwise  
     */    
    public boolean isMultivalue() 
    {
        return false;   
    }
    
}
