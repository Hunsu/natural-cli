/* 
 * WordToken.java
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
public class WordToken extends Token {

    public WordToken(String text) throws InvalidTokenException {
        super(text);
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#getDefinition()
     */
    @Override
    public ITokenDefinition getDefinition() {
        return new WordTokenDefinition();
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#matches(java.lang.String)
     */
    @Override
    public boolean matches(String text) {
        return true;
    }

}
