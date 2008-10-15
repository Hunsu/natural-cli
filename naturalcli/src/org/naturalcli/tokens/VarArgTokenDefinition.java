/* 
 * IVarArgTokenDefinition.java
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

/**
 * @author Ferran Busquets
 *
 */
public class VarArgTokenDefinition implements ITokenDefinition {

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.ITokenDefinition#incompatibleMessage(java.lang.String)
     */
    @Override
    public String incompatibleMessage(String text) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.ITokenDefinition#isIncompatible(java.lang.String)
     */
    @Override
    public boolean isIncompatible(String text) {
        return text.equalsIgnoreCase("...");
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.ITokenDefinition#matches(java.lang.String)
     */
    @Override
    public boolean matches(String text) {
        return this.incompatibleMessage(text) != null;
    }

}
