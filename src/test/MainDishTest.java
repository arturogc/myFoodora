package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import items.Item;
import items.MainDish;
import junit.framework.TestCase;

public class MainDishTest extends TestCase {

	private MainDish dish;
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dish = new MainDish("name", 50, Item.Type.Standard, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMainDish() {
		assertNotNull("Instance not created", dish);
	}

}
