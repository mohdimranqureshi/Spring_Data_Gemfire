package com.dish.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dish.model.Product;
import com.dish.repository.ProductRepository;
import com.gemstone.gemfire.cache.CacheLoader;
import com.gemstone.gemfire.cache.CacheLoaderException;
import com.gemstone.gemfire.cache.LoaderHelper;

/**
 * @author Mohd Imran
 * 
 * A cache loader that loads Product entries from a backing store using a Spring Data Repository
 *
 */
@Component
public class ProductDBLoader implements CacheLoader<Long, Product> {
	
	@Autowired
	private ProductRepository productRepository;
	
	private static Log log = LogFactory.getLog(ProductDBLoader.class);
	
	public void close() {
		
	}

	/*
	 * This Method is used to load the data from the database by ID.
	 */
	public Product load(LoaderHelper<Long, Product> loadHelper) throws CacheLoaderException {
		Long id = Long.parseLong(String.valueOf(loadHelper.getKey()));
		log.debug("loading product id " + id + " from the database");
		return productRepository.findOne(id);
	}

/*	public ProductRepository getProductRepository() {
		return productRepository;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	*/
	
}
