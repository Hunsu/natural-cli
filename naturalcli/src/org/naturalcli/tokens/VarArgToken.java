/* 
 * VarArgToken.java
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

import org.naturalcli.InvalidTokenException;

/**
 * @author Ferran Busquets
 *
 */
public class VarArgToken extends Token {

    public VarArgToken(String text) throws InvalidTokenException {
        super(text);
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#getDefinition()
     */
    @Override
    public ITokenDefinition getDefinition() {
        return new VarArgTokenDefinition();
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#matches(java.lang.String)
     */
    @Override
    public boolean matches(String text) {
        return text.equalsIgnoreCase("...");
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#validateTokenAfter(org.naturalcli.tokens.Token)
     */
    @Override
    public String validateFollowing(Token t) {
        if (t != null)
            return "Variable arguments token only allowed at the end.";
        return super.validateFollowing(t);
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#validateTokenBefore(org.naturalcli.tokens.Token)
     */
    @Override
    public String validatePreceding(Token t) {   
        ParameterToken pt = (ParameterToken) t; 
        if (pt == null || !t.isMandatory())
            return "Variable arguments have to follow a mandatory parameter.";        
        return super.validatePreceding(t);
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#isMultivalue()
     */
    @Override
    public boolean isMultivalue() {
        return true;
    }

}
