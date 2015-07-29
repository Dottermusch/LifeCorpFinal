package data;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import model.DemoAdmin;
import model.DemoCustomer;
import myTools.DBUtil;

public class DemoAdminDB
{
	public static DemoAdmin getCustomerByLogin(String email, String password)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT d FROM DemoAdmin d WHERE d.password = :password AND d.email = :email";
		// String qString = "SELECT d FROM DemoCustomer d WHERE d.custEmail = :email";
		TypedQuery<DemoAdmin> q = em.createQuery(qString, DemoAdmin.class);
		q.setParameter("email", email);
		q.setParameter("password", password);
		
		DemoAdmin customer = null;
		
		try
		{
			customer = q.getSingleResult();
		}
		catch (NoResultException nre)
		{
			System.out.println("A problem occurred retrieveing a customer by login: " + nre);
		}
		finally
		{
			em.close();
		}
		
		return customer;
	}
}
