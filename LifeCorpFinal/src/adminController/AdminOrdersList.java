package adminController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DemoOrderDB;
import model.DemoOrder;
import myTools.DBUtil;

@WebServlet("/AdminOrderList")
public class AdminOrdersList extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	public AdminOrdersList() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if((boolean)session.getAttribute("isAdmin") == true)	// verify user is a valid admin user
			AdminOrdersListAction(request, response);
		else													// otherwise redirect to 404 error page
			getServletContext().getRequestDispatcher("/404").forward(request, response);
		
	}
	
	private void AdminOrdersListAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<DemoOrder> ordList = DemoOrderDB.getAllDemoOrders();
		
		Collections.sort(ordList); 	// sort the orders in inverse chronological order
		
		request.setAttribute("ordList", ordList);
		
		getServletContext().getRequestDispatcher("/adminOrderList.jsp").forward(request, response);
	}

}