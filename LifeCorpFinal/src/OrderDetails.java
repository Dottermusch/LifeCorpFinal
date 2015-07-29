
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DemoOrderDB;
import model.DemoOrder;
import model.DemoOrderItem;

/**
 * Servlet implementation class OrderDetails
 */
@WebServlet("/OrderDetails")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// doPost(request,response);	changed customerOrderHistory.jsp to submit orderId in post vs get
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String orderId = request.getParameter("orderId");
		System.out.println(orderId);
		
		DemoOrder order =  DemoOrderDB.getOrderById(Long.parseLong(orderId));
		
		List<DemoOrderItem> items = order.getDemoOrderItems();
		
		request.setAttribute("orderTotal", order.getOrderTotal());
		
		request.setAttribute("detailedOrder", items);
		getServletContext().getRequestDispatcher("/orderDetails.jsp").forward(request, response);
		
		
	}

}
