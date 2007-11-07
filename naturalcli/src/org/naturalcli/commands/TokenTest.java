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
package org.naturalcli.commands;

import org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ferran Busquets
 *
 */
public class TokenTest {


	/**
	 * Test method for {@link org.naturalcli.commands.Token#isOptional()}.
	 */
	@Test
	public final void testIsOptional() {
		// Ok
		Assert.assertTrue(new Token("[marian]").isOptional());
		// Not ok
		Assert.assertFalse(new Token("marian").isOptional());
		Assert.assertFalse(new Token("marian]").isOptional());
		Assert.assertFalse(new Token("[marian").isOptional());
		Assert.assertFalse(new Token("ma[]rian").isOptional());
		Assert.assertFalse(new Token("<[marian]>").isOptional());
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#isParameter()}.
	 */
	@Test
	public final void testIsParameter() {
		// Ok
		Assert.assertTrue(new Token("<marian>").isParameter());
		Assert.assertTrue(new Token("[<marian>]").isParameter());
		// Not ok
		Assert.assertFalse(new Token("marian").isParameter());
		Assert.assertFalse(new Token("[marian>").isParameter());
		Assert.assertFalse(new Token("marian>").isParameter());
		Assert.assertFalse(new Token("[marian>]").isParameter());
		Assert.assertFalse(new Token("<marian").isParameter());
		Assert.assertFalse(new Token("ma<>rian").isParameter());
		Assert.assertFalse(new Token("<[marian]>").isParameter());
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#getParameterName()}.
	 */
	@Test
	public final void testGetParameterName() {
		Assert.assertNull(new Token("marian").getParameterName());
		Assert.assertEquals("marian", new Token("<marian>").getParameterName());
		Assert.assertEquals("marian", new Token("<marian:integer>").getParameterName());
		Assert.assertEquals("marian", new Token("<marian:email>").getParameterName());
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#getParameterTypeName()}.
	 */
	@Test
	public final void testGetParameterTypeName() {
		Assert.assertNull(new Token("marian").getParameterName());
		Assert.assertEquals("marian", new Token("<marian>").getParameterName());
		Assert.assertEquals("integer", new Token("<marian:integer>").getParameterName());
		Assert.assertEquals("email", new Token("<marian:email>").getParameterName());
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#isIdentifier()}.
	 */
	@Test
	public final void testIsIdentifier() {
		// Ok
		Assert.assertTrue(new Token("marian").isIdentifier());
		Assert.assertTrue(new Token("[marian]").isIdentifier());
		// Not ok
		Assert.assertFalse(new Token("<marian>").isIdentifier());
		Assert.assertFalse(new Token("[marian>").isIdentifier());
		Assert.assertFalse(new Token("marian>").isIdentifier());
		Assert.assertFalse(new Token("[marian>]").isIdentifier());
		Assert.assertFalse(new Token("<marian").isIdentifier());
		Assert.assertFalse(new Token("ma<>rian").isIdentifier());
		Assert.assertFalse(new Token("<[marian]>").isIdentifier());
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#getWord()}.
	 */
	@Test
	public final void testGetWord() {
		Assert.fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.naturalcli.commands.Token#matches(java.lang.String, org.naturalcli.parameters.ParameterValidator)}.
	 */
	@Test
	public final void testMatches() {
		Assert.fail("Not yet implemented");
	}

}
