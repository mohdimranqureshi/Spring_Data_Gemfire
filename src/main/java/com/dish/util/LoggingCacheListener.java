package com.dish.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

/*
 * @author Mohd Imran
 * 
 * This class is working as a listener class of cache.
 * All the events of cache are listen in this class
 */

@SuppressWarnings("rawtypes")
@Component
public class LoggingCacheListener extends CacheListenerAdapter {
	private static Log log = LogFactory.getLog(LoggingCacheListener.class);

	 /*
	  * @param EntryEvent
	  * This method is called after create the data into region.
	  * Its get the region name ,key and value from EntryEvent.
	  */
	@Override
	  public void afterCreate(EntryEvent event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    final Object newValue = event.getNewValue();
	    log.info("In region [" + regionName + "] created key [" + key
	        + "] value [" + newValue + "]");
	  }
		
	/*
	 * @param EntryEvent
	 * This method is called after delete the data from region.
	 * Its get the region name and key from EntryEvent
	 * 
	 */
	  @Override
	  public void afterDestroy(EntryEvent event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    log.info("In region [" + regionName + "] destroyed key [" + key
	        + "]");
	  }

	  /*
	   * @param EntryEvent
	   * This Method is called after update the data in region.
	   * its get the regionName, key ,newvalue and oldValue from EntryEvent
	   * 
	   */
	  @Override
	  public void afterUpdate(EntryEvent event) {
	    final String regionName = event.getRegion().getName();
	    final Object key = event.getKey();
	    final Object newValue = event.getNewValue();
	    final Object oldValue = event.getOldValue();
	    log.info("In region [" + regionName + "] updated key [" + key
	        + "] [oldValue [" + oldValue + "] new value [" + newValue +"]");
	  }
}
