package adminController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.DemoOrder;
import model.DemoProductInfo;
import data.DemoOrderDB;
import data.DemoProductInfoDB;

/**
 * Servlet implementation class AdminProductList
 */
@WebServlet("/AdminProductList")
public class AdminProductList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProductList() {
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
			AdminCustomerProductListAction(request, response);
		else													// otherwise direct to 404 error page
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	private void AdminCustomerProductListAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String url = null;
		
		if(action.equals("loadProducts"))	// handle the initial load of products and categories
		{									// from button selection on initial admin page to 
											// initialize admin product management screen
			url = "/adminProductList.jsp";
			List<DemoProductInfo> products = DemoProductInfoDB.getEagerDemoProducts("All");
			request.setAttribute("adminProducts", products);
			
			// Load the images from the database to the img directory on server
			populateImagesFromDatabase(request, response, products);
			
			// Filter the list to those items selected by category
			List<String> categories = DemoProductInfoDB.getEagerProductCategories();
			session.setAttribute("adminCategories", categories);
		}
		
		else if (action.equals("categoryChanged"))
		{
			url = "/adminProductList.jsp";
			String newCategory = request.getParameter("category");
			List<DemoProductInfo> products = DemoProductInfoDB.getEagerDemoProducts(newCategory);
			session.setAttribute("adminProducts", products);
			session.setAttribute("adminCategory", newCategory);
		}
		
		else if (action.equals("productEdit"))
		{
			url = "/adminEditProduct.jsp";
			String prodId = request.getParameter("productId");
			long productId = Long.parseLong(prodId);
			DemoProductInfo product = DemoProductInfoDB.getProductById(productId);
			request.setAttribute("editProduct", product);
		}
		
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
	
	public int populateImagesFromDatabase(HttpServletRequest request, HttpServletResponse response, List<DemoProductInfo> products) throws ServletException, IOException
	{
		final String IMAGE_DIRECTORY = "img";
	    final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	    final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	    final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	    
		// set variables to provide path names and scope visibility through method
		String fileName = null;
		int numFilesWritten = 0;
         
        // configures file writing settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        
        
        // use factory to provide thread-safe retrieval mechanism to
        // write to eventual application directory
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("")
            + File.separator + IMAGE_DIRECTORY;
        
        // in this case use a predetermined path set at start of method
        // String uploadPath = uploadToDirPath;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        for(DemoProductInfo product : products)
        {
        	String filePath = uploadPath + File.separator + product.getFilename();
        	DemoProductInfoDB.getBytesToFile(product.getProductImage(), filePath);
        	numFilesWritten++;
        }
        
        return numFilesWritten;
	}

}
