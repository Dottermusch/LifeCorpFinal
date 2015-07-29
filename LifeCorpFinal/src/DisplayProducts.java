

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.*;
import data.DemoProductInfoDB;
import data.StateDB;



/**
 * Servlet implementation class DisplayProducts
 */
@WebServlet("/DisplayProducts")
public class DisplayProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayProducts() {
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
		String url = "/displayProducts.jsp";
		String category = request.getParameter("category");
		
		String sessionCategory = (String) session.getAttribute("productCategory");
		
		if (request.getParameter("category") == null && session.getAttribute("productCategory") == null)
		{
			// start up case or user did not make a selection return all categories
			
			// get the entire list of products from database by setting category to "All"
			List<DemoProductInfo> allProducts = DemoProductInfoDB.getProductsByCategory("All");
			
			// populate the applications images file directory on server
			populateImagesFromDatabase(request, response, allProducts);
			
			// post entire list to session variable for retrieval in displayProducts screen
			request.setAttribute("products", allProducts);
			session.setAttribute("productCategory", "All");
			
			if(session.getAttribute("productCategories") == null)	// load the product categories session variable
				session.setAttribute("productCategories", DemoProductInfoDB.getEagerProductCategories());
		}
		else if (request.getParameter("category") == null && session.getAttribute("productCategory") != null)
		{
			// the user has previously selected a category so return filtered list of products 
			category = sessionCategory;
			
			List<DemoProductInfo> products = DemoProductInfoDB.getProductsByCategory(category);
			
			// post filtered list to session variable for retrieval in displayProducts screen
			request.setAttribute("products", products);
			// request.setAttribute("productCategory", category); // not needed as selection has not changed
		}
		
		else if(request.getParameter("category") != null)
		{
			// user has made a selection or selection was persisted from previous setting
			List<DemoProductInfo> products = DemoProductInfoDB.getProductsByCategory(category);
			
			// post filtered list to session variable for retrieval in displayProducts screen
			request.setAttribute("products", products);
			
			session.setAttribute("productCategory", category);
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
