
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DemoProductInfoDB;
import model.DemoProductInfo;
/**
 * Servlet implementation class Details
 */
@WebServlet("/Details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Details() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get the productId to enable search for specific DemoProductInfo object
		String productId = request.getParameter("productId"); 
		
		//calling the getProductById method to retrieve the data.
		DemoProductInfo product = DemoProductInfoDB.getProductById(new Long(productId));
		
		request.setAttribute("productDetails", product);
		
		
		getServletContext().getRequestDispatcher("/details.jsp").forward(request, response); //forwarding it to page Details.jsp, which will display the following info.
		
		
	}

}
