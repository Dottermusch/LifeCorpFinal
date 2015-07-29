package adminController;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
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

import model.DemoProductInfo;
import data.DemoProductInfoDB;

/**
 * Servlet implementation class AdminAddProduct
 */
@WebServlet("/AdminAddProduct")
public class AdminAddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddProduct() {
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
			AdminAddProductAction(request, response);
		else													// otherwise direct to 404 error page
			getServletContext().getRequestDispatcher("/404").forward(request, response);
	}
	
	public void AdminAddProductAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String url = null;
		long productId = 0;
		String fromAddProductPage = (String) request.getParameter("url");
		
		// check to see if request is redirect from other pages or from adminAddProduct.jsp
		if ((fromAddProductPage == null || fromAddProductPage.equals("")) && !ServletFileUpload.isMultipartContent(request))
		{
			// handle redirect from another page and initialize categories
			url = "/adminAddProduct.jsp";
		}
		else if (ServletFileUpload.isMultipartContent(request))
		{
			String uploadedFileName = processFileUpload(request, response);
			if (uploadedFileName != null && !uploadedFileName.equals(""))
			{
				session.setAttribute("uploadedFileName", uploadedFileName);
				request.setAttribute("uploadMessage", "File image successfully uploaded.");
				url = "/adminAddProduct.jsp";
			}
			else
			{
				request.setAttribute("uploadMessage", "A problem occurred uploading the image");
				url = "/adminAddProduct.jsp";
			}
		}
		else if (fromAddProductPage.equals("adminAddProduct")) 	// only true if main form submitted
		{
			// Get the file name and have it loaded to the pre-stage directory
			// to enable setting the object image blob via DemoProductInfoDB.getFileToBytes();
			String fileName = (String) session.getAttribute("uploadedFileName");
			
			// get the pathname created when the upload to the server img directory occurred
			String preStagePathName = (String)session.getAttribute("adminFileUploadPath");
			
		
			DemoProductInfo product = new DemoProductInfo();	// initialize a new product instance
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
			// product.setProductImage(DemoProductInfoDB.getProductById(productId).getProductImage());
			
			// product.setFilename(databasePathName + fileName);
			// use just the filename to access the ./img directory
			// product.setFilename(fileName);
			product.setFilename((String)session.getAttribute("uploadedFileName"));
			product.setMimetype(request.getParameter("mimetype"));
			product.setImageLastUpdate(new Date());
			
			product.setProductImage(DemoProductInfoDB.readFileIntoByteArray(preStagePathName + fileName));
			
			productId = DemoProductInfoDB.addProduct(product);
			
			if(productId > 0)
			{
				session.removeAttribute("uploadedFileName"); 	// clean up the session variable
				request.setAttribute("message", "Creation of " + product.getProductName() + " successful.");
				List<String> categories = DemoProductInfoDB.getEagerProductCategories();	// refresh the product categories
				session.setAttribute("adminCategories", categories);						// in case a new one was added
				product = DemoProductInfoDB.getProductById(productId);	// put updated product in request variable
				session.setAttribute("editProduct", product);			// to populate adminEditProduct.jsp
				url = "/adminEditProduct.jsp";
			}
			else
			{
				request.setAttribute("message", "A problem occurred while updating " + 
						product.getProductName() + ".  Please try again");
				url = "/adminAddProduct.jsp";
			}
			
			
		}
		
		// initialize the categories dropdown box with no "All" selection to force a choice
		// if page gets 
		List<String> categories = DemoProductInfoDB.getEagerProductCategories();
		categories.remove(0); 	// remove "All" selection from categories to force user choice
		request.setAttribute("adminMinusAllCategories", categories);
		
		getServletContext().getRequestDispatcher(url).forward(request, response);
	}
	
	protected String processFileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		// final String UPLOAD_DIRECTORY = "upload";
		final String UPLOAD_DIRECTORY = "img";
	    final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
	    final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	    final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	    
		// set variables to provide path names and scope visibility through method
		String uploadToDirPath = ".\\img";
		String testPath = null;
		String fileName = null;
         
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        
        // Use a predetermined path rather than the default project path with
        // a factory to provide thread-safe retrieval mechanism
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // factory.setRepository(new File(uploadToDirPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        String uploadPath = getServletContext().getRealPath("")
            + File.separator + UPLOAD_DIRECTORY;
        
        session.setAttribute("adminFileUploadPath", uploadPath + File.separator);
        
        // in this case use a predetermined path set at start of method
        // String uploadPath = uploadToDirPath;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
         
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
             
            // iterates over form's fields
            while (iter.hasNext()) 
            {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) 
                {
                    fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    testPath = filePath;
                    File storeFile = new File(filePath);
                     
                    // saves the file on disk
                    item.write(storeFile);
                }
            }
            // request.setAttribute("message", "Upload of file " + testPath + " successfully!");
        } catch (Exception ex) 
        {
            // request.setAttribute("message", "There was an error uploading " + testPath + ": " + ex.getMessage());
        }
        
        return fileName;
	}

}
