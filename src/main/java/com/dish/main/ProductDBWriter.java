package com.dish.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.dish.model.Product;
import com.dish.repository.ProductRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.CacheWriter;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.RegionEvent;

/**
 * @author Mohd imran
 * 
 * A cache writer that synchronizes a backing store using a Spring Data repository
 * Methods annotated @ASync will run in a separate thread if the application context
 * enables these annotations
 * 
 * 
 */
@Component
public class ProductDBWriter implements CacheWriter<Long, Product> {
	 
	@Autowired
	private ProductRepository productRepository;
	
	private static Log log = LogFactory.getLog(ProductDBWriter.class);
	
	public void close() {
		
	}

	/*public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}*/

	/*
	 * This Method is called when data is ready to put into Region.
	 * it called the update method to save the data into region.
	 * 
	 */
	@Async
	public void beforeCreate(EntryEvent<Long, Product> entryEvent) throws CacheWriterException {
		if (productRepository.findOne(Long.parseLong(String.valueOf(entryEvent.getKey()))) == null) {
			update(entryEvent.getNewValue());
		}
	}
	
	/*
	 * this Method is used when we want to delete the data from region.
	 * its called the delete method to delete the data from region.
	 * 
	 */
	@Async
	public void beforeDestroy(EntryEvent<Long, Product> entryEvent) throws CacheWriterException {
		delete(Long.parseLong(String.valueOf(entryEvent.getKey())));
	}

	/*
	 * This method is called before when we want to clear the region.
	 * 
	 */
	public void beforeRegionClear(RegionEvent<Long, Product> entryEvent) throws CacheWriterException {
	
		
	}

	/*
	 * This method is called before when we want to destroy the region.
	 * 
	 */
	public void beforeRegionDestroy(RegionEvent<Long, Product> regionEvent)
			throws CacheWriterException {

	}

	/*
	 * @param entryEvent
	 * If we update the same object of region than this method will used.
	 * it is called update method with new value to update the data.
	 * 
	 */
	@Async
	public void beforeUpdate(EntryEvent<Long, Product> entryEvent) throws CacheWriterException {
		update(entryEvent.getNewValue());
	}
	
	/*
	 * @param product
	 * 
	 * it is called by beforeUpdate and beforeCreate method.
	 * it is used to saved the data into repository,
	 */
	private void update(Product product) {
		log.debug("updating the database");
		productRepository.save(product);
	}
	
	/*
	 * @param id
	 * 
	 * This method is used to delete the data from repository by id.
	 * It is called by beforeDestroy method.
	 */
	private void delete(Long id) {
		log.debug("deleting id " + id + " from the database");
		productRepository.delete(id);
	}
}
