/* 
 * ParameterToken.java
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
import org.naturalcli.ParameterValidator;
import org.naturalcli.UnknownParameterType;

/**
 * @author Ferran Busquets
 *
 */
public class ParameterToken extends Token {

    private String parameter_name;
    private String parameter_type;
    private ParameterValidator validator;

    public ParameterToken(String text) throws InvalidTokenException {
        super(text);
    }

    public ParameterToken(String text, ParameterValidator pv) throws InvalidTokenException {
        super(text);
        validator = pv;
    }
    
    /**
     * @return the validator
     */
    public ParameterValidator getValidator() {
        return validator;
    }

    /**
     * @param validator the validator to set
     */
    public void setValidator(ParameterValidator validator) {
        this.validator = validator;
    }

    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#setText(java.lang.String)
     */
    @Override
    public void setText(String text) throws InvalidTokenException {
        super.setText(text);        
        int i = text.indexOf(":");
        if (i == -1)
        {
            this.parameter_name = text;
            this.parameter_type = text;
        }
        else
        {
            this.parameter_name = text.substring(0, i);
            this.parameter_type = text.substring(i+1);
        }
    }

    @Override
    public ITokenDefinition getDefinition() {
        return new ParameterTokenDefinition();
    }

    @Override
    public boolean matches(String text) {
        return this.matches(text, this.validator);
    }

    public boolean matches(String text, ParameterValidator validator) {
        try {
            return validator.validate(text, parameter_type) == null;
        } catch (UnknownParameterType e) {
            throw new RuntimeException("Cannot match parameter", e);
        }
    }    
    /* (non-Javadoc)
     * @see org.naturalcli.tokens.Token#validateTokenBefore(org.naturalcli.tokens.Token)
     */
    @Override
    protected void validatePreceding(Token t) throws InvalidTokenException {
        ParameterToken pt = (ParameterToken) t; 
        if (pt.isOptional() && pt.parameter_type.equalsIgnoreCase(this.getParameterType()))
          throw new InvalidTokenException("An optional parameter cannot be followed by a parameter of the same type.");
        super.validatePreceding(t);
    }

    /**
     * @return the parameter_name
     */
    public String getParameterName() {
        return parameter_name;
    }

    /**
     * @return the parameter_type
     */
    public String getParameterType() {
        return parameter_type;
    }
    
    
}
