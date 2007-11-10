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
package org.naturalcli;

import org.junit.Assert;
import org.junit.Test;
import org.naturalcli.ParameterValidator;

/**
 * @author Ferran Busquets
 *
 */
public class ParameterValidatorTest {

	/**
	 * Test method for {@link org.naturalcli.ParameterValidator#validate(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testValidate() {
		ParameterValidator pv = new ParameterValidator();
		try {
			// Right 
			Assert.assertNull(pv.validate("test@test.afdf", "email"));
			Assert.assertNull(pv.validate("hello", "identifier"));
			Assert.assertNull(pv.validate("1234", "integer"));
			Assert.assertNull(pv.validate("asdadsa", "string"));
			// Bad 
			Assert.assertNotNull(pv.validate("", "email"));
			Assert.assertNotNull(pv.validate("hello", "email"));
			Assert.assertNotNull(pv.validate("1234", "email"));
			Assert.assertNotNull(pv.validate("", "identifier"));
			Assert.assertNotNull(pv.validate("test@test.afdf", "identifier"));
			Assert.assertNotNull(pv.validate("1234", "identifier"));
			Assert.assertNotNull(pv.validate("", "integer"));
			Assert.assertNotNull(pv.validate("hello", "integer"));
			Assert.assertNotNull(pv.validate("test@test.afdf", "integer"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
