/* 
 * TokenFactory.java
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
public class TokenFactory {
    
   public static Token getToken(String text) throws InvalidTokenException
   {  
       Token t;
       // Parameter
       t = checkTokenDefinition(text, new ParameterTokenDefinition());
       if (t != null) return t;
       // VarArg
       t = checkTokenDefinition(text, new VarArgTokenDefinition());
       if (t != null) return t;
       // Work token
       return new WordToken(text);
   }

   /**
    * @param text
    * @param def
    * @throws InvalidTokenException
    */
   private static Token checkTokenDefinition(String text, ITokenDefinition def)
   throws InvalidTokenException 
   {
       String error;
       if (def.matches(text))
           return new ParameterToken(text);
       error = def.incompatibleMessage(text);
       if (error != null)
           throw new InvalidTokenException(error);
       return null;
   }
}
