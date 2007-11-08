/* 
 * SyntaxTest.java
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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ferran Busquets
 *
 */
public class SyntaxTest {

	private ParameterValidator pv;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pv = new ParameterValidator();
	}

	/**
	 * Test method for {@link org.naturalcli.Syntax#Syntax(java.lang.String)}.
	 */
	@Test
	public final void testSyntax() {
	}

	/**
	 * Test method for {@link org.naturalcli.Syntax#parse(java.lang.String[], int, org.naturalcli.ParameterValidator)}.
	 */
	@Test
	public final void testParse() {
		String s;
		try {
			// 
			s = "marian is the best";
			assertTrue(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "a", "b", "c", "marian", "is", "the", "best"}, 3, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 1, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "is", "the", "best", "really"}, 0, pv));
			// 
			s = "marian <hi:integer> the <bye:identifier>";
			assertTrue(new Syntax(s).parse(new String[] { "marian", "3", "the", "best"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "marian", "31231", "the", "best"}, 0, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "-1", "the", "best"}, 0, pv));
			assertFalse(new Syntax(s).parse(new String[] { "marian", "1.2", "the", "best"}, 0, pv));
			// 
			s = "marian [<hi:integer>] the <bye:identifier>";
			assertTrue(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "marian", "1", "the", "best"}, 0, pv));
			//
			s = "marian [<hi:integer>] the [<hello:identifier>] <bye:identifier>";
			assertTrue(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "marian", "the", "best", "really"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "marian", "1", "the", "best"}, 0, pv));
			assertTrue(new Syntax(s).parse(new String[] { "marian", "1", "the", "best", "really"}, 0, pv));
		} catch (Exception e) {
			fail();
		}		
	}

	/**
	 * Test method for {@link org.naturalcli.Syntax#getParameterIndexs()}.
	 */
/*	@Test
	public final void testGetParameterIndexs() {
		try {
			assertEquals(0, new Syntax("marian is the best").getParameterIndexs().length);
			assertEquals(2, new Syntax("marian <hi:integer> the <bye:identifier>").getParameterIndexs().length);
			assertEquals(2, new Syntax("marian [<hi:integer>] the <bye:identifier>").getParameterIndexs().length);
			assertEquals(3, new Syntax("marian [<hi:integer>] the [<hello:identifier>] <bye:identifier>").getParameterIndexs().length);
		} catch (Exception e) {
			fail();
		}		
	}
*/
}
