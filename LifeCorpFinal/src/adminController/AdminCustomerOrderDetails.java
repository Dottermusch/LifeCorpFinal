package adminController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DemoOrderDB;
import model.DemoOrder;

@WebServlet("/AdminCustomerOrderDetails")
public class AdminCustomerOrderDetails extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	public AdminCustomerOrderDetails() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if((boolean)session.getAttribute("isAdmin") == true)	// verify user is admin user and direct to helper 
			AdminCustomerOrderDetailsAction(request, response);	// method to retrieve order detail information
		else
			// if not an admin user, redirect to 404 page as user should not have access to the admin pages
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	private void AdminCustomerOrderDetailsAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String ordId = request.getParameter("id");
		DemoOrder custOrd = DemoOrderDB.getOrderById((new Long(ordId)));	// get customer to display in order details page		
		
		request.setAttribute("custOrd", custOrd);
		request.setAttribute("ordItemList", custOrd.getDemoOrderItems());
		
		getServletContext().getRequestDispatcher("/adminCustomerOrderDetails.jsp").forward(request, response);
	}
	
}
