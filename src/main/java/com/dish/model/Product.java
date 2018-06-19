package com.dish.model;

import java.io.Serializable;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.data.gemfire.support.Expiration;
import org.springframework.data.gemfire.support.IdleTimeoutExpiration;
import org.springframework.data.gemfire.support.TimeToLiveExpiration;

/*
 * @author Mohd Imran
 * 
 * This is a POJO class which extends the AbstractPersistentEntity and implements the Serializable interface.
 * It is used to create gemfire region.
 * @Region annotation is used to initialize the gemfire region.
 * 
 * @TimeToLiveExpiration The amount of time, in seconds, 
 * the object may remain in the cache after the last creation or update. For entries,
 * the counter is set to zero for create and put operations. 
 * Region counters are reset when the Region is created and when an entry has its counter reset.
 * 
 * @IdleTimeoutExpiration The amount of time, in seconds, the object may remain in the cache after the last access. 
 * The Idle Timeout counter for an object is reset any time its TTL counter is reset.
 * The Idle Timeout counter for a Region is reset whenever the Idle Timeout is reset for one of its entries.
 * 
 * Both @IdleTimeoutExpiration and @TimeToLiveExpiration take precedence over the generic @Expiration annotation.
 * 
 * Though, neither @IdleTimeoutExpiration nor @TimeToLiveExpiration overrides the other,
 * rather they may compliment each other when different Region Entry Expiration types, 
 * such as TTL and TTI, are configured.
 */
@Region("Product")
@javax.persistence.Entity
@TimeToLiveExpiration(timeout = "3600", action = "LOCAL_DESTROY")
@IdleTimeoutExpiration(timeout = "1800", action = "LOCAL_INVALIDATE")
@Expiration(timeout = "1800", action = "INVALIDATE")
public class Product extends AbstractPersistentEntity implements Serializable{
	
	private static final long serialVersionUID = 831295555713696643L;
	
	private String name;
	private String description;
	private double price;
	
	public Product(){
		
	}
	
	@PersistenceConstructor
	public Product(Long id, String name,  double d, String description) {
		super(id);

		this.name = name;
		this.price = d;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
