/* 
 * IdentifierParamTypeTest.java
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
public class IdentifierParamTypeTest {

	/**
	 * Test method for {@link org.naturalcli.parameters.IdentifierParamType#validateParameter(java.lang.String)}.
	 */
	@Test
	public final void testValidateParameter() {
		IdentifierParamType t = new IdentifierParamType();
		// Right 
		Assert.assertTrue(t.validateParameter("a"));
		Assert.assertTrue(t.validateParameter("asdasdasd_asdasda"));
		Assert.assertTrue(t.validateParameter("a12342"));
		Assert.assertTrue(t.validateParameter("a_1"));
		// Bad 
		Assert.assertFalse(t.validateParameter("aas-asdsd-"));
		Assert.assertFalse(t.validateParameter("d d"));
		Assert.assertFalse(t.validateParameter("d d"));
		Assert.assertFalse(t.validateParameter("1234"));
		Assert.assertFalse(t.validateParameter("asdad.asdasda"));
		Assert.assertFalse(t.validateParameter("_asdsada"));
	}
}
