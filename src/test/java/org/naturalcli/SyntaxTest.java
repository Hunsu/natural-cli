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
import org.naturalcli.InvalidSyntaxException;
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
	 * @throws InvalidSyntaxException 
	 */
	@Test
	public final void testSyntax() throws InvalidSyntaxException {
		new Syntax("marian is the best");
		new Syntax("marian [is] [the] best");
		new Syntax("marian [<integer>] [the] best");
		new Syntax("marian is [<integer>] <identifier>");
		new Syntax("marian is <integer> ...");
		try {
			new Syntax("marian is [<integer>] ...");
			fail();
		} catch (InvalidSyntaxException e) { 
		}
		try {
			new Syntax("marian is ...");
			fail();
		} catch (InvalidSyntaxException e) { 
		}
		try {
			new Syntax("marian is the [<best:identifier>] <really:identifier>");
			fail();
		} catch (InvalidSyntaxException e) { 
		}

		try {
			new Syntax("marian is ... [<best:identifier>] <really:identifier>");
			fail();
		} catch (InvalidSyntaxException e) { 
		}
		
	}
	

	/**
	 * Test method for {@link org.naturalcli.Syntax#parse(java.lang.String[], int, org.naturalcli.ParameterValidator)}.
	 * @throws InvalidSyntaxException 
	 * @throws UnknownParameterType 
	 * @throws InvalidSyntaxException 
	 * @throws UnknownParameterType 
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testParse() throws UnknownParameterType, InvalidSyntaxException {
		String s; // Current syntax definition
		ParseResult pr; // Current parse result
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
		  assertTrue(Arrays.equals(new Object[] { 3, "best"}, pr.getParameterValues()));
		pr = new Syntax(s).parse(new String[] { "marian", "31231", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 31231, "best"}, pr.getParameterValues()));
		assertNull(new Syntax(s).parse(new String[] { "marian", "is", "the", "best"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1.2", "the", "best"}, 0, pv));
		// 
		s = "marian [<hi:integer>] the <bye:identifier>";
		pr = new Syntax(s).parse(new String[] { "marian", "1", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 1, "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { null, "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, false, true, true}, pr.getTokensGiven()));		  
		//
		s = "marian [<hi:integer>] the [<hello:integer>] <bye:identifier>";
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { null, null, "best"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, false, true, false, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "1", "the", "123", "really"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 1, 123, "really"}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] {true, true, true, true, true}, pr.getTokensGiven()));		  
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "123"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the", "best", "really"}, 0, pv));
		assertNull(new Syntax(s).parse(new String[] { "marian", "1", "the"}, 0, pv));
		//
		s = "marian [<integer>] the [<identifier>] <integer>";
		pr = new Syntax(s).parse(new String[] { "marian", "the", "best", "123"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { null, "best", 123}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, false, true, true, true}, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "the", "123"}, 0, pv);
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { null, null, 123}, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, false, true, false, true}, pr.getTokensGiven()));		  
		//
		s = "marian is the <integer> ...";
		pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "1" }, 0, pv); 
		  assertNotNull(pr);
    	  assertEquals(1, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 1 }, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, true, true, true, false }, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "1", "2"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(2, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 1, 2 }, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, true, true, true, true }, pr.getTokensGiven()));		  
		pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "1", "2", "3"}, 0, pv); 
		  assertNotNull(pr);
		  assertEquals(3, pr.getParameterCount());
		  assertTrue(Arrays.equals(new Object[] { 1, 2, 3 }, pr.getParameterValues()));
		  assertTrue(Arrays.equals(new boolean[] { true, true, true, true, true }, pr.getTokensGiven()));		  
        //
        s = "marian is the best [woman]";
        pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "best", "woman" }, 0, pv); 
          assertNotNull(pr);
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] { true, true, true, true, true }, pr.getTokensGiven()));        
        pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "best" }, 0, pv); 
          assertNotNull(pr);
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] { true, true, true, true, false }, pr.getTokensGiven()));        
        //
        s = "marian [is] [the] [best]";
        pr = new Syntax(s).parse(new String[] { "marian", "is", "the", "best" }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, true, true, true}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian", "is", "the"         }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length); 
          assertTrue(Arrays.equals(new boolean[] {true, true, true, false }, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian", "is",        "best" }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, true, false, true}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian", "is",               }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, true, false, false}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian",       "the", "best" }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, false, true, true}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian",       "the"         }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, false, true, false}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian",              "best" }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length);
          assertTrue(Arrays.equals(new boolean[] {true, false, false, true}, pr.getTokensGiven()));
        pr = new Syntax(s).parse(new String[] { "marian",                     }, 0, pv); 
          assertNotNull(pr);  
          assertEquals(0, pr.getParameterCount());
          assertEquals(0, pr.getParameterValues().length); 
          assertTrue(Arrays.equals(new boolean[] {true, false, false, false}, pr.getTokensGiven()));
	}


}
