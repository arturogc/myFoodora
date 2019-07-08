package test;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import deliveryPolicies.FastestDelivery;
import orders.Order;
import users.Courier;
import users.ExistingUserException;
import users.Restaurant;

public class FastestDeliveryTestCase {

	private Order order;
	
	private ArrayList<Courier> couriers;
	
	private FastestDelivery deliveryPolicy;
	
	public FastestDeliveryTestCase() {

	
	}

	@Before
	public void setUp() throws Exception {
		deliveryPolicy = new FastestDelivery();

		couriers = new ArrayList<Courier>();
		try {
			Restaurant restaurant = new Restaurant(null, "resto du love", "resto1", "azerty", new Point2D.Double(0,0));
			order = new Order(null, null,restaurant, null, null);

			couriers.add(new Courier("courier name 1", "surname 1", "username1", "alligator", new Point2D.Double(2,6)));
			couriers.add(new Courier("courier name 2", "surname 2", "username2", "alligator", new Point2D.Double(10,25)));
			couriers.add(new Courier("courier name 3", "surname 3", "username3", "alligator", new Point2D.Double(10,50)));
			couriers.add(new Courier("courier name 4", "surname 4", "username4", "alligator", new Point2D.Double(3,2)));	
		} catch (ExistingUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		Restaurant.getIds().clear();
		Restaurant.getUsernames().clear();
		Courier.getIds().clear();
		Courier.getUsernames().clear();	
	}

	@Test
	public void testFairOccupationDelivery() {
		assertNotNull("Instance not created", deliveryPolicy);
	}

	@Test
	public void testAllocateCourier() {
		Courier courier = deliveryPolicy.allocateCourier(order, couriers);
		assertEquals("Not fair Allocation", courier, couriers.get(3));
		assertNotNull("Not Allocated", courier);

	}


}
