package adminController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DemoCustomerDB;
import model.DemoOrder;

@WebServlet("/AdminCustomerOrderHistory")
public class AdminCustomerOrderHistory extends HttpServlet 
{
	private static final long serialVersionUID = 1L; 
	
    public AdminCustomerOrderHistory() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		if((boolean)session.getAttribute("isAdmin") == true)	// verify user is authenticated admin user
			AdminCustomerOrderHistoryAction(request, response);
		else													// otherwise direct to 404 error page
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	private void AdminCustomerOrderHistoryAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String custId = request.getParameter("id");
		List<DemoOrder> demoOrd = DemoCustomerDB.getCustomerById(new Long(custId)).getDemoOrders();
		
		Collections.sort(demoOrd); 	// sort the order list in inverse chronological order
		
		request.setAttribute("single", true);
		request.setAttribute("ordList", demoOrd);
		
		getServletContext().getRequestDispatcher("/adminOrderList.jsp").forward(request, response);	
	}

}
