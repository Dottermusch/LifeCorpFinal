

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DemoCustomer;
import model.DemoOrder;

/**
 * Servlet implementation class CustomerOrderHistory
 */
@WebServlet("/CustomerOrderHistory")
public class CustomerOrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerOrderHistory() {
        super();
    
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);	// leave this method in to allow customer Order History pick in nav bar to work
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "/customerOrderHistory.jsp";
		
		
		HttpSession session = request.getSession();
		
		DemoCustomer customer =  (DemoCustomer)session.getAttribute("customer");
		
		List<DemoOrder> orders = customer.getDemoOrders();
		
		if(orders == null || orders.isEmpty())
		{
			url = "/noCustomerOrderHistory.jsp";
		}
		
		Collections.sort(orders); 	// sort the orders in inverse chrononlogical order
		
		request.setAttribute("allOrders", orders);
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
		
	}

}
