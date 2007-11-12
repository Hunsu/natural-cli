package org.naturalcli;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.naturalcli.Command;
import org.naturalcli.InvalidSyntaxDefinionException;
import org.naturalcli.commands.NullCommandExecutor;

/**
 * @author Ferran Busquets
 *
 */
public class CommandTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link org.naturalcli.Command#isHidden()}.
	 * @throws InvalidSyntaxDefinionException 
	 */
	@Test
	public final void testIsHidden() throws InvalidSyntaxDefinionException {
		assertTrue(new Command("marian is the best", ". Hello world", new NullCommandExecutor()).isHidden());
		assertFalse(new Command("marian is the best", "Hello world", new NullCommandExecutor()).isHidden());
	}

}
