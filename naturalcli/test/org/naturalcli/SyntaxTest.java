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
import org.naturalcli.InvalidSyntaxDefinionException;
import org.naturalcli.ParameterValidator;
import org.naturalcli.Syntax;
import org.naturalcli.UnknownParameterType;

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
	 * @throws InvalidSyntaxDefinionException 
	 */
	@Test
	public final void testSyntax() throws InvalidSyntaxDefinionException {
		new Syntax("marian is the best");
		new Syntax("marian [is] [the] best");
		new Syntax("marian [<integer>] [the] best");
		new Syntax("marian is [<integer>] <identifier>");
		try {
			new Syntax("marian is the [<best:identifier>] <really:identifier>");
			fail();
		} catch (InvalidSyntaxDefinionException e) { 
		}
		
	}
	
	/**
	 * Test method for {@link org.naturalcli.Syntax#parse(java.lang.String[], int, org.naturalcli.ParameterValidator)}.
	 * @throws InvalidSyntaxDefinionException 
	 * @throws UnknownParameterType 
	 * @throws InvalidSyntaxDefinionException 
	 * @throws UnknownParameterType 
	 */
	@Test
	public final void testParse() throws UnknownParameterType, InvalidSyntaxDefinionException {
		String s;
		// 
		s = "marian is the best";
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
		assertNotNull(new Syntax(s).parse(new String[] { "a", "b", "c", "marian", "is", "the", "best"}, 3, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 1, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best", "really"}, 0, pv));
		// 
		s = "marian <hi:integer> the <bye:identifier>";
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "3", "the", "best"}, 0, pv));
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "31231", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "-1", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1.2", "the", "best"}, 0, pv));
		// 
		s = "marian [<hi:integer>] the <bye:identifier>";
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "best"}, 0, pv));
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
		//
		s = "marian [<hi:integer>] the [<hello:integer>] <bye:identifier>";
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "123", "really"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "123"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "best", "really"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the"}, 0, pv));
		//
		s = "marian [<integer>] the [<identifier>] <integer>";
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "the", "best", "123"}, 0, pv));
		assertNotNull(new Syntax(s).parse(new String[] { "marian", "the", "123"}, 0, pv));
	}


}
