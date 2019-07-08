package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Dessert;
import items.Item;
import junit.framework.TestCase;

public class DessertTest extends TestCase {

	private Dessert dessert;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dessert = new Dessert("name", 50, Item.Type.Standard, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDessert() {
		assertNotNull("Instance not created", dessert);
	}

}
