package dataTest;

import static org.junit.Assert.*;
import model.DemoAdmin;

import org.junit.Test;

import data.DemoAdminDB;

public class DemoAdminDBTest
{

	@Test
	public void testGetCustomerByLogin()
	{
		String email = "admin@lifecorp.com";
		String password = "password";
		DemoAdmin admin;
		
		try
		{
			admin = DemoAdminDB.getCustomerByLogin(email, password);
			assert((admin.getEmail().equals("admin@lifecorp.com") && (admin.getPassword().equals("password"))));
					
		}
		catch (Exception e)
		{
			fail("Either admin username or password incorrectly retrieved: " + e);
		}
	}

}
