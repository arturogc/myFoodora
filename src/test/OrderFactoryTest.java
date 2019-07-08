package test;

import static org.junit.Assert.*;

import org.junit.Test;

import orders.Order;
import orders.OrderFactory;


public class OrderFactoryTest {

	private OrderFactory order_factory = new OrderFactory();

	@Test
	public void testCreateOrder() {
		Order order = order_factory.CreateOrder(null, null, null, null, null);
		assertNotNull(order);
	}

}
