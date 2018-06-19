package com.dish.main;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.dish.model.Product;
import com.dish.repository.ProductRepository;

import com.gemstone.gemfire.cache.Region;

/*
 * @author Mohd Imran
 * 
 * This class is used to start the Gemfire Server.
 * Get the app-context.xml from context.
 * app-context.xml import the cache-config.xml which is used to create Gemfire cache and region.
 * If we used database then at first call it get the data from table and cached into region.
 * next time it will retrieve the data from cache instead of database. 
 * 
 */

public class Main {
	
	private static Log log = LogFactory.getLog(Main.class);
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"app-context.xml");
		
		Region<Long,Product> region = context.getBean(Region.class);
    	ProductRepository productRepository = context.getBean(ProductRepository.class);
		
    	
		for (long i = 1; i<=3; i++){
			Product p = region.get(i);
			log.debug("Retrieved product " + p.getName() + " from cache");
			System.out.println("Retrieved product " + p.getName() + " from cache");
		}
		
		 //Let's try this again
		
		log.debug("2nd pass...This time the CacheLoader is not called since items are already cached");
		for (long i = 1; i<=3; i++){
			Product p = region.get(i);
			log.debug("Retrieved product " + p.getName() + " from cache");
			System.out.println("Retrieved product " + p.getName() + " from cache");
		}		
		
		//Create a new product
		log.debug("Adding a new product to the cache...");
		
		BigDecimal bd = new BigDecimal(299.99);
		double d = bd.doubleValue();
		
		Product iphone = new Product(4L, "Apple IPhone", d,"Smart phone");
		region.put(iphone.getId(), iphone);
		
		while (productRepository.count() == 3) {
			log.debug("Product repository still has : " + productRepository.count() + " rows");
			System.out.println("count in Product repository : " +productRepository.count() + " rows");
			Thread.sleep(10);
		}
		System.out.println("count in Product repository : " +productRepository.count() + " rows");
		log.debug("Product repository now has : " + productRepository.count() + " rows");
		
		log.debug("Removing a product from the cache...");
		region.destroy(4L);
		
		while (productRepository.count() == 4) {
			log.debug("Product repository still has : " + productRepository.count() + " rows");
			Thread.sleep(10);
		}
		log.debug("Product repository now has : " + productRepository.count() + " rows"); 
		System.out.println("Now count in Product repository : " +productRepository.count() + " rows ");
	}

}
