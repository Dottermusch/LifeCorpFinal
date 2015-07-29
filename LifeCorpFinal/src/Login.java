

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DemoAdminDB;
import data.DemoCustomerDB;
import model.DemoCustomer;
import model.DemoOrder;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		long custId=0;
		String url = null;
		String action = request.getParameter("action");
		session.removeAttribute("message1");
		session.removeAttribute("message2");
		
		
		
		if(action.equals("existingCustomer"))
		{
			DemoCustomer customer = null;
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			boolean isAdmin = false;
			
			// first check the DemoAdmin table to see if the credentials
			// supplied match the admin entry in the DEMO_ADMIN table
			if(DemoAdminDB.getCustomerByLogin(email, password) != null)	
			{
				isAdmin = true;
				session.setAttribute("isAdmin", isAdmin);
				url = "/admin.jsp";
			}
			else
			{
				customer = DemoCustomerDB.getCustomerByLogin(email, password);
			}
			
			if (customer != null)	// existing customer object returned from login
			{
				if (session.getAttribute("order") != null)	// check if order build in progress
				{
					// order in progress - get existing customer, order, add customer to order
					// and set URL
					session.setAttribute("customer", customer);
					// session.setAttribute("lastFour", "XXXX-XXXX-XXXX-" + customer.getCreditCard().substring(15));
					// provision for credit card checking if we want to activate the capability
					session.setAttribute("lastFour", "XXXX-XXXX-XXXX-XXXX");
					DemoOrder order = (DemoOrder)session.getAttribute("order");
					order.setDemoCustomer(customer);
					url = "/orderCart.jsp";
					session.setAttribute("order", order);
				}
				else	// no order in progress return user to product list
				{
					session.setAttribute("customer", customer);
					url = "/DisplayProducts";
				}
			}
			else if(!isAdmin)	// no customer returned - if admin, user will be directed to /admin.jsp no user was found 
			{					// in the database and is not admin, bad login attempt so direct back to /login.jsp page
				url = "/login.jsp";
				request.setAttribute("message1", "Password or user email missing/incorrect.");
			}
		}
		else if(action.equals("newCustomer"))	// new customer - get parameters from login.jsp to build new customer
		{
			boolean isInputPasswordsMatch = false;
			
			System.out.println("Get new customer details and create customer");
			DemoCustomer customer = new DemoCustomer();
			customer.setCustFirstName(request.getParameter("firstName"));
			customer.setCustLastName(request.getParameter("lastName"));
			customer.setCustEmail(request.getParameter("email"));
			customer.setPhoneNumber1(request.getParameter("phone"));
			customer.setCustPassword(request.getParameter("password"));
			customer.setCustStreetAddress1(request.getParameter("shipToStreet"));
			customer.setCustCity(request.getParameter("shipToCity"));
			customer.setCustState(request.getParameter("shipToState"));
			customer.setCustPostalCode(request.getParameter("shipToZip"));
			customer.setCreditLimit(new BigDecimal("1000"));
			
			String password = request.getParameter("password");
			String passwordDup = request.getParameter("passwordDup");
			
			isInputPasswordsMatch = (password.equals(passwordDup));
			
			DemoCustomer testExistCustomer = DemoCustomerDB.doesCustomerEmailExist(customer.getCustEmail());
			
			if (testExistCustomer == null && isInputPasswordsMatch)	// test for user with same email before insertion to preclude duplicates
			{
				custId = DemoCustomerDB.insertCustomerReturnId(customer);
				customer.setCustomerId(custId); 	// update customerId with returned value
			}
			else
			{
				custId = 0; 	// confirm unchanged value initialized above indicating duplicate found
			}
			
			if(custId != 0)	// new customer created without duplication;
			{
				if (session.getAttribute("order") != null)	// check if order in progress
				{
					// order in progress - get existing customer, order, add customer to order
					// and set URL
					session.setAttribute("customer", customer);
					// session.setAttribute("lastFour", "XXXX-XXXX-XXXX-" + customer.getCreditCard().substring(15));
					session.setAttribute("lastFour", "XXXX-XXXX-XXXX-XXXX");
					DemoOrder order = (DemoOrder)session.getAttribute("order");
					order.setDemoCustomer(customer);
					url = "/orderCart.jsp";
					session.setAttribute("order", order);
				}
				else	// no order in progress return user to product list
				{
					session.setAttribute("customer", customer);
					// session.setAttribute("lastFour", "XXXX-XXXX-XXXX-" + customer.getCreditCard().substring(15));
					// session.setAttribute("lastFour", "XXXX-XXXX-XXXX-XXXX");
					url = "/DisplayProducts";
					// url = "/index.jsp";
				}
			}
			else	// customer ID = 0 indicates a problem or duplicate creating customer 
					// - send user back to login page
			{
				if(isInputPasswordsMatch)
					request.setAttribute("message2", "Input passwords did not match please try again.");
				else
					request.setAttribute("message2", "Problem creating account or account already exists - please try again.");
				
				url = "/login.jsp";
				
			}
		}
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
