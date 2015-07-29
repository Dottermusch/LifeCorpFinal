package dataTest;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.List;

import model.DemoCustomer;
import model.DemoOrder;

import org.junit.Test;

import data.DemoCustomerDB;
import data.DemoOrderDB;

public class DemoCustomerDBTest
{

	@Test
	public void testInsertCustomerReturnId()
	{
		DemoCustomer customer = new DemoCustomer();
		customer.setCustomerId((long) 0);	// set to zero in case this makes a difference during insert and return of key field
		customer.setCustFirstName("Gomer");
		customer.setCustLastName("Pyle");
		customer.setCustEmail("pyle@gmail.com");
		customer.setCustPassword("password");
		customer.setCreditLimit(new BigDecimal(1000));
		customer.setCustStreetAddress1("123 Mainstreet");
		customer.setCustCity("Mayberry");
		customer.setCustState("NC");
		customer.setCustPostalCode("29807");
		customer.setPhoneNumber1("919-555-1212");
		
		// patch to account for usual key return not providing the correct customerId
		long custId = DemoCustomerDB.insertCustomerReturnId(customer);
		customer.setCustomerId(custId);
		
		try
		{
			assertTrue(custId > 0);
		}
		catch (Exception e)
		{
			fail("InsertCustomerReturnId method failed: " + e);
		}
		
		// clean up customer table entry
		boolean isDeleted = false;
		
		if (custId > 0)
			isDeleted = DemoCustomerDB.deleteCustomer(customer);
		System.out.println("Clean-up from InsertCustomerReturnId occurred correctly: " + isDeleted);
	}

	@Test
	public void testGetCustomerById()
	{
		DemoCustomer customer = DemoCustomerDB.getCustomerById((long) 1);
		boolean isCustIdReturnedOK = false;
		boolean isEmailReturnedOK = false;
		
		isCustIdReturnedOK = (customer.getCustomerId() == (long) 1);
		isEmailReturnedOK = (customer.getCustEmail().equals("dulles@gmail.com"));
		
		try
		{
			assertTrue(isCustIdReturnedOK && isEmailReturnedOK);
		}
		catch (Exception e)
		{
			fail("GetCustomerById method failed: " + e);
		}
	}

	@Test
	public void testGetCustomerByLogin()
	{
		String email = "dulles@gmail.com";
		String password = "password";
		DemoCustomer customer = DemoCustomerDB.getCustomerByLogin(email, password);
		
		try
		{
			assertEquals(customer.getCustLastName(), "Dulles");
		}
		catch (Exception e)
		{
			fail("Method getCustomerByLogin did not provide expected last name: " + e);
		}
	}

	@Test
	public void testDoesCustomerEmailExist()
	{
		String email = "dulles@gmail.com";
		
		DemoCustomer customer = DemoCustomerDB.doesCustomerEmailExist(email);
		
		try
		{
			assertEquals(customer.getCustLastName(), "Dulles");
		}
		catch (Exception e)
		{
			fail("Method doesCustomerEmailExist did not provide expected last name: " + e);
		}
	}
	
	@Test
	public void testDeleteCustomer()
	{
		String email = null;
		boolean isDeleted = false;
		
		DemoCustomer customer = new DemoCustomer();
		customer.setCustFirstName("Gomer");
		customer.setCustLastName("Pyle");
		customer.setCustEmail("pyle@gmail.com");
		customer.setCustPassword("password");
		customer.setCreditLimit(new BigDecimal(1000));
		customer.setCustStreetAddress1("123 Mainstreet");
		customer.setCustCity("Mayberry");
		customer.setCustState("NC");
		customer.setCustPostalCode("29807");
		customer.setPhoneNumber1("919-555-1212");
		
		// patch to account for usual key return not providing the correct customerId
		long custId = DemoCustomerDB.insertCustomerReturnId(customer);
		customer.setCustomerId(custId);
		
		// set email to later search for customer by email
		email = customer.getCustEmail();
		
		if (custId > 0)
			isDeleted = DemoCustomerDB.deleteCustomer(customer);
		System.out.println("Clean-up from InsertCustomerReturnId occurred correctly: " + isDeleted);
		
		try
		{
			assertTrue(DemoCustomerDB.doesCustomerEmailExist(email) == null);
		}
		catch (Exception e)
		{
			fail("Delete Customer method failed: " + e);
		}
	}
	
	@Test
	public void testGetAllDemoCustomers()
	{
		List<DemoCustomer> demoCustomers = DemoCustomerDB.getAllDemoCustomers();
		boolean isDemoCustomer = (demoCustomers.get(1).getClass().equals(DemoCustomer.class));
		
		try
		{
			assertTrue((demoCustomers.size() > 0) && isDemoCustomer);
		}
		catch (Exception e)
		{
			fail("A problem occurred while attempting retrieve all DemoOrders: " + e);
		}
	}
	
	
	@Test
	public void testGetRefreshedCustomerById()
	{
		long customerId = 1;	// set index for customer John Dulles
		int startNumOrders = 0;
		int endNumOrders = 0;
		
		// get starting state of customer under test
		DemoCustomer startCustomer = DemoCustomerDB.getCustomerById(customerId);
		startNumOrders = startCustomer.getDemoOrders().size();
		
		// generate a compatible order to add for this test customer
		DemoOrder testOrderToAdd = (DemoOrder) deepClone(startCustomer.getDemoOrders().get(0));	
		testOrderToAdd.setOrderId(0);
		
		// add the order
		long orderId = DemoOrderDB.insertOrderReturnId(testOrderToAdd);
		
		// get what should be an updated version of same customer with additional order
		DemoCustomer endCustomer = DemoCustomerDB.getRefreshedCustomerById(customerId);
		
		endNumOrders = endCustomer.getDemoOrders().size();
		
		try
		{
			assertTrue(endNumOrders - startNumOrders == 1);
			System.out.println("Debug record in assert portion of testGetRefreshedCustomerById()");
		}
		catch (Exception e)
		{
			fail("Customer from testGetRefreshedCustomerById() not refreshed as expected: " + e);
		}
		
		// clean up the database by removing the added test order
		finally
		{
			boolean isDeleted = DemoOrderDB.deleteOrder(DemoOrderDB.getOrderById(orderId));
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
