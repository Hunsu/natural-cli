/* 
 * EmailParamTypeTest.java
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
public class EmailParamTypeTest {

	/**
	 * Test method for {@link org.naturalcli.parameters.EmailParamType#validateParameter(java.lang.String)}.
	 */
	@Test
	public final void testValidateParameter() {
		EmailParamType ept = new EmailParamType();
		// Right email addresses
		Assert.assertTrue(ept.validateParameter("asadad@asasda.com"));
		Assert.assertTrue(ept.validateParameter("asadad@asdasd.asda.das.das.das.d.asasda.com"));
		Assert.assertTrue(ept.validateParameter("asadad.d.d.sad.d@asdasd.asda.das.das.das.d.asasda.com"));
		// Bad email addresses
		Assert.assertFalse(ept.validateParameter("as as as d@ asd asd."));
		Assert.assertFalse(ept.validateParameter("as as as d@ asd asd.com"));
		Assert.assertFalse(ept.validateParameter("@ asd asd.com"));
		Assert.assertFalse(ept.validateParameter("asdasd@"));
		Assert.assertFalse(ept.validateParameter("asdasd"));
		Assert.assertFalse(ept.validateParameter("asdasd@sadasd"));
	}

}
