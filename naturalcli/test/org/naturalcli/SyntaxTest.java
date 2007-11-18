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

import java.util.Arrays;

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
	@SuppressWarnings("deprecation")
	@Test
	public final void testParse() throws UnknownParameterType, InvalidSyntaxDefinionException {
		String s;
		ParseResult pr;
		// 
		s = "marian is the best";
		pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv); 
  		  assertNotNull(pr);
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
		  assertEquals(0, pr.getParameterCount());
		  assertEquals(0, pr.getParameterValues().length);
		pr = new Syntax(s).parse(new String[] { "a", "b", "c", "marian", "is", "the", "best"}, 3, pv);
          assertNotNull(pr);
          assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
		  assertEquals(0, pr.getParameterCount());
		  assertEquals(0, pr.getParameterValues().length);
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 1, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best", "really"}, 0, pv));
		// 
		s = "marian <hi:integer> the <bye:identifier>";
		pr = new Syntax(s).parse(new String[] { "marian", "3", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { "3", "best"}, pr.getParameterValues()));
		pr = new Syntax(s).parse(new String[] { "marian", "31231", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { "31231", "best"}, pr.getParameterValues()));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "-1", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1.2", "the", "best"}, 0, pv));
		// 
		s = "marian [<hi:integer>] the <bye:identifier>";
		pr = new Syntax(s).parse(new String[] { "marian", "1", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { "1", "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { null, "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, false, true, true}, pr.getTokensGiven()));		  
		//
		s = "marian [<hi:integer>] the [<hello:integer>] <bye:identifier>";
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { null, null, "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, false, true, false, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "1", "the", "123", "really"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { "1", "123", "really"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true, true}, pr.getTokensGiven()));		  
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "123"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "best", "really"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the"}, 0, pv));
		//
		s = "marian [<integer>] the [<identifier>] <integer>";
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best", "123"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { null, "best", "123"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, false, true, true, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "the", "123"}, 0, pv);
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new String[] { null, null, "123"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, false, true, false, true}, pr.getTokensGiven()));		  
	}


}
