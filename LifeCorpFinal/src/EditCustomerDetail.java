

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DemoCustomerDB;
import model.DemoCustomer;

/**
 * Servlet implementation class EditCustomerDetail
 */
@WebServlet("/EditCustomerDetail")
public class EditCustomerDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCustomerDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// String[] parsePreviousURL = request.getHeader("referer").split("/"); 
		// String previousURL = parsePreviousURL[parsePreviousURL.length-1];
		String previousURL = request.getParameter("url");
		String url = null;
		long custId = 0;
		DemoCustomer customer = null;;
		
		// Infer the action from the calling page rather than from a hidden input field value
		if (previousURL.equals("adminCustomerList.jsp"))
		{
			custId = Integer.parseInt(request.getParameter("id"));
			customer = DemoCustomerDB.getRefreshedCustomerById(custId);
			request.setAttribute("editCustomer", customer);
			url = "/editCustomer.jsp";
		}
		else if (previousURL.equals("editCustomer.jsp"))
		{
			boolean isInputPasswordsMatch = false;
			
			System.out.println("Get new customer details and create customer");
			customer = new DemoCustomer();	// reinitialize customer - previous data above gone
			customer.setCustomerId(Long.parseLong(request.getParameter("custId")));
			customer.setCustFirstName(request.getParameter("firstName"));
			customer.setCustLastName(request.getParameter("lastName"));
			customer.setCustEmail(request.getParameter("email"));
			customer.setPhoneNumber1(request.getParameter("phone"));
			customer.setCustPassword(request.getParameter("password"));
			customer.setCustStreetAddress1(request.getParameter("shipToStreet"));
			customer.setCustCity(request.getParameter("shipToCity"));
			customer.setCustState(request.getParameter("shipToState"));
			customer.setCustPostalCode(request.getParameter("shipToZip"));
			String creditLimit = request.getParameter("creditLimit");
			customer.setCreditLimit(new BigDecimal(creditLimit));
			
			String password = request.getParameter("password");
			String passwordDup = request.getParameter("passwordDup");
			
			isInputPasswordsMatch = (password.equals(passwordDup));
			
			if(isInputPasswordsMatch)
			{
				url = "/AdminCustomerList";	// default url assuming update goes OK
				boolean isUpdated = DemoCustomerDB.updateCustomer(customer);
				
				if(!isUpdated)	// check for unanticipated problem with update
				{
					request.setAttribute("message2", "Input passwords did not match please try again.");
					url = "/editCustomer.jsp";
				}
			}
			else	// passwords did not match on data coming back from edit page - try again
			{
				request.setAttribute("message2", "Input passwords did not match please try again.");
				url = "/editCustomer.jsp";
			}
			
		}
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
