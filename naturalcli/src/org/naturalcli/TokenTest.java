/* 
 * TokenTest.java
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

import org.junit.Test;

/**
 * @author Ferran Busquets
 *
 */
public class TokenTest {

	/**
	 * Test method for {@link org.naturalcli.Token#isOptional()}.
	 */
	@Test
	public final void testSetText() throws InvalidTokenException {
		// ok
		new Token("marian");
		new Token("<marian>");
		new Token("[marian]");
		new Token("[<marian>]");
		// not ok
		try { new Token("marian]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("marian>"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("marian>]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<marian"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<marian]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<marian>]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<[marian"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<[marian>"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("<[marian>]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("[marian"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("[marian>"); fail(); } catch (InvalidTokenException e) { }
		try { new Token("[marian>]"); fail(); } catch (InvalidTokenException e) { }
		try { new Token(""); fail(); } catch (InvalidTokenException e) { }
		try { new Token(null); fail(); } catch (InvalidTokenException e) { }
	}
	
	/**
	 * Test method for {@link org.naturalcli.Token#isOptional()}.
	 */
	@Test
	public final void testIsOptional() throws InvalidTokenException {
		// true
		assertTrue(new Token("[marian]").isOptional());
		// false
		assertFalse(new Token("marian").isOptional());
		assertFalse(new Token("ma[]rian").isOptional());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#isParameter()}.
	 */
	@Test
	public final void testIsParameter() throws InvalidTokenException {
		// true
		assertTrue(new Token("<marian>").isParameter());
		assertTrue(new Token("[<marian>]").isParameter());
		// false
		assertFalse(new Token("marian").isParameter());
		assertFalse(new Token("ma<>rian").isParameter());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#isOptionalParameter()}.
	 */
	@Test
	public final void testIsOptionalParameter() throws InvalidTokenException {
		// true
		assertTrue(new Token("[<marian>]").isOptionalParameter());
		assertTrue(new Token("[<marian:integer>]").isOptionalParameter());
		// false
		assertFalse(new Token("marian").isOptionalParameter());
		assertFalse(new Token("[marian]").isOptionalParameter());
		assertFalse(new Token("<marian:integer>").isOptionalParameter());
	}
	
	/**
	 * Test method for {@link org.naturalcli.Token#getParameterName()}.
	 */
	@Test
	public final void testGetParameterName() throws InvalidTokenException {
		// null
		assertNull(new Token("marian").getParameterName());
		// equals
		assertEquals("marian", new Token("<marian>").getParameterName());
		assertEquals("marian", new Token("<marian:integer>").getParameterName());
		assertEquals("marian", new Token("<marian:email>").getParameterName());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#getParameterTypeName()}.
	 */
	@Test
	public final void testGetParameterTypeName() throws InvalidTokenException {
		// null
		assertNull(new Token("marian").getParameterTypeName());
		// equals
		assertEquals("integer", new Token("<integer>").getParameterTypeName());
		assertEquals("integer", new Token("<marian:integer>").getParameterTypeName());
		assertEquals("email", new Token("<marian:email>").getParameterTypeName());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#isIdentifier()}.
	 */
	@Test
	public final void testIsIdentifier() throws InvalidTokenException {
		// Ok
		assertTrue(new Token("marian").isIdentifier());
		assertTrue(new Token("[marian]").isIdentifier());
		// Not ok
		assertFalse(new Token("<marian>").isIdentifier());
		assertFalse(new Token("[<marian>]").isIdentifier());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#getWord()}.
	 */
	@Test
	public final void testGetWord() throws InvalidTokenException {
		assertEquals("marian", new Token("<marian>").getWord());
		assertEquals("marian", new Token("[marian]").getWord());
		assertEquals("marian:integer", new Token("<marian:integer>").getWord());
		assertEquals("marian:email", new Token("[<marian:email>]").getWord());
	}

	/**
	 * Test method for {@link org.naturalcli.Token#matches(java.lang.String, org.naturalcli.parameters.ParameterValidator)}.
	 * @throws UnknownParameterType 
	 */
	@Test
	public final void testMatches() throws InvalidTokenException, UnknownParameterType {
		ParameterValidator pv = new ParameterValidator();
		// Ok
		assertTrue(new Token("marian").matches("marian", pv));
		assertTrue(new Token("<integer>").matches("1234", pv));
		assertTrue(new Token("<marian:email>").matches("marian@marian.org", pv));
		// Not ok
		assertFalse(new Token("marian").matches("1234", pv));
		assertFalse(new Token("marian").matches(null, pv));
		assertFalse(new Token("marian").matches("", pv));
		assertFalse(new Token("<integer>").matches("marian", pv));
		assertFalse(new Token("<marian:email>").matches("marian", pv));
	}

}
