package adminController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DemoProductInfoDB;
import model.DemoProductInfo;

/**
 * Servlet implementation class AdminEditProductDetail
 */
@WebServlet("/AdminEditProductDetail")
public class AdminEditProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEditProductDetail() {
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
		HttpSession session = request.getSession();
		if((boolean)session.getAttribute("isAdmin") == true)	// verify user is authenticated admin user
			AdminCustomerEditProductAction(request, response);
		else													// otherwise direct to 404 error page
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	public void AdminCustomerEditProductAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		DemoProductInfo product = new DemoProductInfo();
		boolean isUpdatedOK = false;
		String url = "/adminEditProduct.jsp";
		long productId = (long)Long.parseLong(request.getParameter("productId"));
		product.setProductId(productId);
		product.setProductName(request.getParameter("productName"));
		product.setProductDescription(request.getParameter("productDescription"));
		
		// check the category field to see if a new category is to be created
		if(request.getParameter("addCategory") != null && 				// null if unchecked - verify
				request.getParameter("newCategory") != null && 			// new category is provided
				!request.getParameter("newCategory").equals(""))  
			product.setCategory(request.getParameter("newCategory"));
		else
			product.setCategory(request.getParameter("productCategory"));
		
		product.setProductAvail(request.getParameter("productAvail"));
		product.setListPrice(new BigDecimal(request.getParameter("listPrice")));
		
		// get the product image from the database for now
		product.setProductImage(DemoProductInfoDB.getProductById(productId).getProductImage());
		
		product.setFilename(request.getParameter("filename"));
		product.setMimetype(request.getParameter("mimetype"));
		product.setImageLastUpdate(new Date());
		
		isUpdatedOK = DemoProductInfoDB.updateProduct(product);
		if(isUpdatedOK)
		{
			request.setAttribute("message", "Product update successful.");
			List<String> categories = DemoProductInfoDB.getEagerProductCategories();	// refresh the product categories
			session.setAttribute("adminCategories", categories);						// in case a new one was added
		}
		else
			request.setAttribute("message", "A problem occurred while updating " + 
					product.getProductName());
		
		product = DemoProductInfoDB.getProductById(productId);	// put updated product in request variable
		request.setAttribute("editProduct", product);			// to populate adminEditProduct.jsp
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

}
