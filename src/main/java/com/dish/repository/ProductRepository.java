package com.dish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.dish.model.Product;

/**
 * @author Mohd Imran
 * A Product Repository to perform simple CRUD operations
 * 
 *
 */
@Repository
public  interface ProductRepository extends CrudRepository<Product,Long> {
	
	
}
