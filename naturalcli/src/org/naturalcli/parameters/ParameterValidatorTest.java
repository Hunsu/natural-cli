/* 
 * ParameterValidatorTest.java
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
package org.naturalcli.parameters;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ferran Busquets
 *
 */
public class ParameterValidatorTest {

	/**
	 * Test method for {@link org.naturalcli.parameters.ParameterValidator#validate(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testValidate() {
		ParameterValidator pv = new ParameterValidator();
		try {
			// Right 
			Assert.assertTrue(pv.validate("test@test.afdf", "email") == null);
			Assert.assertTrue(pv.validate("hello", "identifier") == null);
			Assert.assertTrue(pv.validate("1234", "integer") == null);
			Assert.assertTrue(pv.validate("asdadsa", "string") == null);
			// Bad 
			Assert.assertFalse(pv.validate("", "email") == null);
			Assert.assertFalse(pv.validate("hello", "email") == null);
			Assert.assertFalse(pv.validate("1234", "email") == null);
			Assert.assertFalse(pv.validate("", "identifier") == null);
			Assert.assertFalse(pv.validate("test@test.afdf", "identifier") == null);
			Assert.assertFalse(pv.validate("1234", "identifier") == null);
			Assert.assertFalse(pv.validate("", "integer") == null);
			Assert.assertFalse(pv.validate("hello", "integer") == null);
			Assert.assertFalse(pv.validate("test@test.afdf", "integer") == null);
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
