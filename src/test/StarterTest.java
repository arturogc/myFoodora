package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Item;
import items.Starter;
import junit.framework.TestCase;

public class StarterTest extends TestCase{
	Starter starter;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		starter = new Starter("name", 50, Item.Type.Standard, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStarter() {
		assertNotNull("Instance not created", starter);
	}

}
