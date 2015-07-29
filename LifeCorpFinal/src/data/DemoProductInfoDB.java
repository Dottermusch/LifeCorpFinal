package data;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.*;
import myTools.DBUtil;

public class DemoProductInfoDB 
{
	public static List<DemoProductInfo> getProductsByCategory(String category)
	{
		List<DemoProductInfo> allProducts = null;
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT d FROM DemoProductInfo d WHERE d.category = :category "
				+ "OR :category = 'All'";
		TypedQuery<DemoProductInfo> q = em.createQuery(qString, DemoProductInfo.class);
		q.setParameter("category", category);
		
		try
		{
			allProducts = q.getResultList();
			if (allProducts == null || allProducts.isEmpty())
				allProducts = null;
		}
		catch (Exception e)
		{
			System.out.println("An exception occurred in getAllProducts()" + e);
		}
		finally 
		{
			em.close();
		}
		
		return allProducts;
	}
	
	public static DemoProductInfo getProductById(long productId)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		try
		{
			DemoProductInfo product = em.find(DemoProductInfo.class, productId);
			return product;
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred in getProductById: " + e);
			return null;
		}
		finally
		{
			em.clear();
		}
	}
	
	public static List<DemoProductInfo> getEagerDemoProducts(String category)
	{
		List<DemoProductInfo> allProducts = null;
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT d FROM DemoProductInfo d WHERE d.category = :category "
				+ "OR :category = 'All'";
		TypedQuery<DemoProductInfo> q = em.createQuery(qString, DemoProductInfo.class);
		q.setParameter("category", category);
		
		q.setHint("javax.persistence.cache.storeMode", "REFRESH");
		
		try
		{
			allProducts = q.getResultList();
			if (allProducts == null || allProducts.isEmpty())
				allProducts = null;
		}
		catch (Exception e)
		{
			System.out.println("An exception occurred in getAllProducts()" + e);
		}
		finally 
		{
			em.close();
		}
		
		return allProducts;
	}
	
	public static List<String> getEagerProductCategories()
	{
		List<String> categories = null;
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT DISTINCT d.category FROM DemoProductInfo d ORDER BY d.category";
		TypedQuery<String> q = em.createQuery(qString, String.class);
		
		q.setHint("javax.persistence.cache.storeMode", "REFRESH");
		
		try
		{
			categories = q.getResultList();
			if (categories == null || categories.isEmpty())
				categories = null;
			else
			{
				categories.add(0, "All");
			}
		}
		catch (Exception e)
		{
			System.out.println("An exception occurred in getAllProducts()" + e);
		}
		finally 
		{
			em.close();
		}
		
		return categories;
	}
	
	
	public static long addProduct(DemoProductInfo product)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		long id;
		
		try
		{
			trans.begin();
			em.persist(product);
			em.flush();
			id = product.getProductId();
			trans.commit();
			
			// patch to correct that product ID returned from method above is erroneous if table trigger is enabled
			// return getMaxProductId();
			// MUST USE THE FIX ABOVE IF AUTO-INCREMENT TRIGGER IS ENABLED AS INDEX WILL OTHERWISE BE WRONG
			
			return id;
		}
		catch (Exception e)
		{
			trans.rollback();
			System.out.println("A problem occurred while inserting the product: " + e);
			return -1;
		}
		finally
		{
			em.close();
		}	
	}
	
	
	public static boolean updateProduct (DemoProductInfo product)
	{
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		
		try
		{
			em.merge(product);
			trans.commit();
			return true;			
		}
		catch(Exception e)
		{
			System.out.println("A problem occurred updating product " + product.getProductName() 
					+ ": " + e);
			trans.rollback();
			return false;
		}
		finally
		{
			em.close();
		}
		
	}
	
	public static FileInputStream getFileToBytes(String filename)
	{
		/*
    	 * 1. How to convert an image file to  byte array?
    	 */
 
        File file = new File(filename);
        FileInputStream fis = null;
        
        try
        {
        	fis = new FileInputStream(file);
        //  create FileInputStream which obtains input bytes from a file in a file system
        //  FileInputStream is meant for reading streams of raw bytes such as image data. 
        //	For reading streams of characters, consider using FileReader.
        }
        catch (FileNotFoundException fnfe)
        {
        	fis = null;
        }
        return fis;
	}
	
	public static int getBytesToFile(byte[] image, String fileNamePath)
	{
		try
		{
			// String CanonicalPath = new File(".").getCanonicalPath();
			FileOutputStream out = new FileOutputStream(fileNamePath, false);
			// OutputStream out = new FileOutputStream("./images/" + product.getFilename());
			out.write(image);
			out.close();
		}
		catch (Exception e)
		{
			System.out.println("A problem occurred while attempting to create the image file: " + e);
			return 0;
		}
	
	return image.length;
	}
	
	
	// reading a byte array from a file
	public static byte[] readFileIntoByteArray(String fileName)
	{
		log("Reading in binary file named : " + fileName);
	    File file = new File(fileName);
	    log("File size: " + file.length());
	    long length = 0;
	    length = file.length();
	    byte[] result = null;
	    try 
	    {
	      InputStream input =  new BufferedInputStream(new FileInputStream(file));
	      result = readAndClose(input);
	    }
	    catch (FileNotFoundException ex)
	    {
	      log(ex);
	    }
	    return result;
	  }
	
	
	// method supporting the method above reads the bytes from the 
	// 
	public static byte[] readAndClose(InputStream aInput)
	{
		//carries the data from input to output :    
		byte[] bucket = new byte[32*1024]; 
		ByteArrayOutputStream result = null; 
		try  
		{
			  try 
			  {
				  //Use buffering? No. Buffering avoids costly access to disk or network;
				  //buffering to an in-memory stream makes no sense.
				  result = new ByteArrayOutputStream(bucket.length);
				  int bytesRead = 0;
				  while(bytesRead != -1)
				  {
					  //aInput.read() returns -1, 0, or more :
					  bytesRead = aInput.read(bucket);
					  if(bytesRead > 0)
					  {
						  result.write(bucket, 0, bytesRead);
					  }
				  }
			  }
			  finally 
			  {
				  aInput.close();
				  //result.close(); this is a no-operation for ByteArrayOutputStream
			  }
		}
		catch (IOException ex)
	    {
			log(ex);
	    }
	    return result.toByteArray();
    }
	
	private static void log(Object aThing)
	{
	    System.out.println(String.valueOf(aThing));
	}
}
