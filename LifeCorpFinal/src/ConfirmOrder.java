

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DemoCustomer;
import model.DemoOrder;
import data.DemoCustomerDB;
import data.DemoOrderDB;


/**
 * Servlet implementation class ConfirmOrder
 */
@WebServlet("/ConfirmOrder")
public class ConfirmOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String url = "/thanks.jsp";
		long orderId = 0;
		DemoOrder order = (DemoOrder)session.getAttribute("order");
		
		if (order.getDemoCustomer() == null)
		{
			DemoCustomer customer = (DemoCustomer)session.getAttribute("customer");
			order.setDemoCustomer(customer);
		}
		
		orderId = DemoOrderDB.insertOrderReturnId(order);
		
		if (orderId > 1)	// in case of success, send order number, update customer data, and set in session
		{
			request.setAttribute("orderId", orderId);
			session.removeAttribute("order");
			DemoCustomer customer = (DemoCustomer)session.getAttribute("customer");
			long customerId = customer.getCustomerId();
			
			// Use special class below to refresh EntityManager before reading DemoCustomer
			// expensive to do so user only where needed
			DemoCustomer updatedCustomer = DemoCustomerDB.getRefreshedCustomerById(customerId);
			session.setAttribute("customer", updatedCustomer);
		}
		else	// Error occurred in posting order - provide notification to user on thank you page
		{
			String message = "There was a problem placing your order please try again later or call our customer care center.";
			request.setAttribute("message", message);
		}
	
		request.setAttribute("orderId", orderId);
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
