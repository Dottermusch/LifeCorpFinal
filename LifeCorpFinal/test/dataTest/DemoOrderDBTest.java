package dataTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import model.DemoCustomer;
import model.DemoOrder;
import model.DemoOrderItem;
import model.DemoProductInfo;

import org.junit.Test;

import data.DemoCustomerDB;
import data.DemoOrderDB;
import data.DemoProductInfoDB;

public class DemoOrderDBTest
{

	@Test
	public void testPresetOrderTotal()
	{
		BigDecimal total = new BigDecimal(0);
		BigDecimal knownTotalFromDb = new BigDecimal(1890);
		long orderId = 1;
		
		DemoOrder order = DemoOrderDB.getOrderById(orderId);
		order.setOrderTotal(total);	// set order to incorrect total of zero to enable test
		
		DemoOrderDB.presetOrderTotal(order);	// call method to reset order to proper total
		
		try
		{
			assertEquals(order.getOrderTotal(), knownTotalFromDb);
		}
		catch (Exception e)
		{
			fail("Error in testPresetOrderTotal method - not resetting total: " + e);
		}
		
	}

	@Test
	public void testInsertOrderReturnId()
	{
		boolean isDeleted = false;
		long orderId = 0;
		DemoCustomer customer = DemoCustomerDB.getCustomerById((long) 7);
		DemoOrder order = DemoOrderDB.getOrderById((long) 93);
		
		order.setDemoCustomer(customer);
		order.setOrderId((long)0);
		
		orderId = DemoOrderDB.insertOrderReturnId(order);
		
		try
		{
			assertTrue(orderId > 0);
		}
		catch (Exception e)
		{
			fail("A problem occurred while attempting to insert an order");
		}
		
		isDeleted = DemoOrderDB.deleteOrder(order);	// clean up after inserting dummy order
		
	}

	@Test
	public void testGetOrderById()
	{
		long orderId = 93;
		DemoOrder order = DemoOrderDB.getOrderById(orderId);
		
		try
		{
			assertEquals(order.getOrderId(), (long) 93);
		}
		catch (Exception e)
		{
			fail("A problem occurred while attempting to get an order by ID: " + e);
		}
	}
	
	@Test
	public void testDeleteOrder()
	{
		boolean isDeleted = false;
		long orderId = 0;
		DemoCustomer customer = DemoCustomerDB.getCustomerById((long) 6);
		DemoOrder order = new DemoOrder();
		DemoOrderItem item = new DemoOrderItem();
		DemoProductInfo product = DemoProductInfoDB.getProductById((long) 1);
		
		order.setDemoOrderItems(new ArrayList<DemoOrderItem>());
		item.setQuantity(new BigDecimal(2));
		item.setUnitPrice(product.getListPrice());
		item.setDemoProductInfo(product);
		
		
		order.setDemoCustomer(customer);
		order.addDemoOrderItem(item);
		
		order.setOrderId((long)0);
		
		orderId = DemoOrderDB.insertOrderReturnId(order);	// insert a dummy order to delete
		
		isDeleted = DemoOrderDB.deleteOrder(order);	// clean up after inserting dummy order
		
		try
		{
			assertTrue(isDeleted && orderId != -1);
		}
		catch (Exception e)
		{
			fail("A problem occurred while attempting to delete an order: " + e);
		}
	}
	
	@Test
	public void testGetAllDemoOrders()
	{
		List<DemoOrder> demoOrders = DemoOrderDB.getAllDemoOrders();
		boolean isDemoOrder = (demoOrders.get(1).getClass().equals(DemoOrder.class));
		
		try
		{
			assertTrue((demoOrders.size() > 0) && isDemoOrder);
		}
		catch (Exception e)
		{
			fail("A problem occurred while attempting retrieve all DemoOrders: " + e);
		}
	}
	
	@Test
	public void testAddCartOrderItemQuantity()
	{
		DemoCustomer dulles = DemoCustomerDB.getCustomerById((long) 3);
		List<DemoOrder> orders = dulles.getDemoOrders();
		DemoOrder order = orders.get(0);
		List<DemoOrderItem> items = orders.get(0).getDemoOrderItems();
		DemoOrderItem item = items.get(0);
		
		// use deep clone to create independent object for test
		DemoOrderItem testItem = (DemoOrderItem) deepClone(item);
		BigDecimal originalQuantity = item.getQuantity();
		testItem.setQuantity(new BigDecimal(5));
		
		DemoOrderItem outItem = order.addCartOrderItem(testItem);
		BigDecimal outputItemQuantity = outItem.getQuantity();
		boolean isChanged = !outputItemQuantity.equals(originalQuantity);
		boolean isEqualNewQuantity = testItem.getQuantity().equals(new BigDecimal(5));
		
		try
		{
			assertTrue(isChanged && isEqualNewQuantity);
		}
		catch (Exception e)
		{
			fail("A problem occurred setting item quanity in ddCartOrderItem method: " + e);
		}
		
	}
	
	@Test
	public void testAddCartOrderItemAddToItemList()
	{
		int originalLength = 0;
		int finalLength = 0;
		DemoCustomer dulles = DemoCustomerDB.getCustomerById((long) 3);
		List<DemoOrder> orders = dulles.getDemoOrders();
		DemoOrder order = orders.get(0);
		List<DemoOrderItem> items = orders.get(0).getDemoOrderItems();
		
		// capture original number of order items for later comparison
		originalLength = items.size();
		
		// get a random item as a template to modify product object
		DemoOrderItem item = items.get(0);
		
		// get a product not in current item list
		DemoProductInfo newProduct = DemoProductInfoDB.getProductById((long)10);
		
		// use deep clone to create independent object for test
		DemoOrderItem testItem = (DemoOrderItem) deepClone(item);
	
		// substitute new product item for original to force an add to item list
		testItem.setDemoProductInfo(newProduct);
		
		// execute the method under test to add the item
		order.addCartOrderItem(testItem);
		
		finalLength = order.getDemoOrderItems().size();
		
		try
		{
			assertTrue (finalLength - originalLength == 1);
		}
		catch (Exception e)
		{
			fail("A problem occurred adding an item in addCartOrderItem method: " + e);
		}
		
	}
	
	@Test
	public void testRemoveCartOrderItem()
	{
		int originalLength = 0;
		int finalLength = 0;
		DemoCustomer dulles = DemoCustomerDB.getCustomerById((long) 3);
		List<DemoOrder> orders = dulles.getDemoOrders();
		DemoOrder order = orders.get(0);
		List<DemoOrderItem> items = orders.get(0).getDemoOrderItems();
		
		// capture original number of order items for later comparison
		originalLength = items.size();
		
		// get an item from the item list to remove from the order
		DemoOrderItem item = items.get(0);
		
		// call method to remove the order item
		order.removeCartOrderItem(item);
		
		finalLength = order.getDemoOrderItems().size();
		
		try
		{
			assertTrue (originalLength - finalLength == 1);
		}
		catch (Exception e)
		{
			fail("A problem occurred deleting an item in removeCartOrderItem method: " + e);
		}
	}
	
	@Test
	public void testCompareTo()
	{
		String initialOrderString = "0";
		String finalOrderString = "0";
		
		GregorianCalendar gDate1 = new GregorianCalendar(2015, 6, 15);
		GregorianCalendar gDate2 = new GregorianCalendar(2015, 5, 14);
		GregorianCalendar gDate3 = new GregorianCalendar(2015, 4, 13);
		
		DemoOrder order1 = new DemoOrder();
		DemoOrder order2 = new DemoOrder();
		DemoOrder order3 = new DemoOrder();
		
		order1.setOrderTimestamp(gDate1.getTime());
		order1.setOrderId(1);
		order1.setOrderTotal(new BigDecimal(1890));
		
		order2.setOrderTimestamp(gDate2.getTime());
		order2.setOrderId(2);
		order2.setOrderTotal(new BigDecimal(2380));
		
		order3.setOrderTimestamp(gDate3.getTime());
		order3.setOrderId(3);
		order3.setOrderTotal(new BigDecimal(1640));
		
		List<DemoOrder> testOrders = new ArrayList<DemoOrder>();
		
		// now add the orders out of order
		testOrders.add(order2);
		testOrders.add(order1);
		testOrders.add(order3);
		
		for (DemoOrder t : testOrders)	// iterate to get the unsorted order IDs into a string
		{
			initialOrderString += t.getOrderId();
		}
		
		Collections.sort(testOrders);
		
		for (DemoOrder t : testOrders)	// iterate to get the unsorted order IDs into a string
		{
			finalOrderString += t.getOrderId();
		}
		
		try 
		{
			assertTrue(finalOrderString.equals("0123") && !finalOrderString.equals(initialOrderString));
		}
		catch (Exception e)
		{
			fail("The implementation of Comparable interface in DemoOrder not working: " + e);
		}
	}
	
	
	 public static Object deepClone(Object object) 
	 {
		   try {
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(baos);
		     oos.writeObject(object);
		     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		     ObjectInputStream ois = new ObjectInputStream(bais);
		     return ois.readObject();
		   }
		   catch (Exception e) {
		     e.printStackTrace();
		     return null;
		   }
	 }

}
