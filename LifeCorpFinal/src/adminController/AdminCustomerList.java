package adminController;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DemoCustomerDB;
import model.DemoCustomer;

@WebServlet("/AdminCustomerList")
public class AdminCustomerList extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	public AdminCustomerList() 
    {
        super();
    }
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{}

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if((boolean)session.getAttribute("isAdmin") == true)	// check to verify admin user arrived at page
			AdminCustomerListAction(request, response);			// if so, get the customer list in helper method
																// below.
		
		else
			// if not an admin user, redirect to the 404 page as user shouldn't get to admin pages
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	
	private void AdminCustomerListAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<DemoCustomer> custList = DemoCustomerDB.getEagerlyAllDemoCustomers();			
		
		request.setAttribute("custList", custList);
		
		getServletContext().getRequestDispatcher("/adminCustomerList.jsp").forward(request, response);
	}
	
}
