package com.evictory.inventorycloud.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

//import com.evictory.inventorycloud.StockServiceApplicationTests;
//import com.evictory.inventorycloud.StockServiceApplicationTests;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;

//@CacheEvict(value = "first", allEntries = true)




@Transactional
public class StockServiceTest {//extends StockServiceApplicationTests {

	@Autowired
	private StockServiceImpl stockServiceImpl;

	@Before
	public void setUp() {
		//stockServiceImpl.
	}

	/*
	 * @Test public void testFetchAll() {
	 * 
	 * Collection<Stock> list = stockServiceImpl.fetchAll();
	 * Assert.assertNotNull("failure exxpected", list);
	 * Assert.assertEquals("failure expected size", 2, list.size());
	 * 
	 * }
	 */

	
	
	  @Test 
	  public void testCreate() {
	  
	  //List<StockDetails> details = new ArrayList<StockDetails>(); 
	  Stock entity =new Stock(); 
	  entity.setReason(""); 
	  entity.setUser(2); 
	  entity.setId(9);
	  //entity.setStockDetails(details);
	  entity.setDate(null);
	  
	  
	  Boolean createdEntity = stockServiceImpl.saveEntry(entity);
	  
	  //System.out.println(createdEntity); 
	  Assert.assertNotNull("failure - expected not null", createdEntity); 
	  Assert.assertNotNull("failure - expected id attribute not null", createdEntity.booleanValue()); 
	  Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.booleanValue()); 
	  //Collection<Stock> list =stockServiceImpl.fetchAll(); 
	 // Assert.assertEquals("failure - expected size", 3, list.size());
	  
	  }
	 
	
	
	/*
	 * @Test public void testUpdate() {
	 * 
	 * Integer id = 1; Stock stock=new Stock(); Boolean entity
	 * =stockServiceImpl.updateEntry(id, stock);
	 * Assert.assertNotNull("failure - expected not null", entity); String
	 * updatedText = entity+ " test"; entity.valueOf(updatedText); Boolean
	 * updatedEntity = stockServiceImpl.updateEntry(id, stock);
	 * Assert.assertNotNull("failure - expected not null", updatedEntity);
	 * Assert.assertEquals("failure - expected id attribute match", id,
	 * updatedEntity.booleanValue());
	 * Assert.assertEquals("failure - expected text attribute match", updatedText,
	 * updatedEntity.booleanValue());
	 * 
	 * }
	 */
	 
	 
	  
	  
	/*
	 * @Test public void testDelete() {
	 * 
	 * Integer id = 1;
	 * 
	 * Stock entity = stockServiceImpl.fetchEntry(id);
	 * 
	 * Assert.assertNotNull("failure - expected not null", entity);
	 * 
	 * stockServiceImpl.deleteEntry(id);
	 * 
	 * Collection<Stock> list = stockServiceImpl.fetchAll();
	 * 
	 * Assert.assertEquals("failure - expected size", 1, list.size());
	 * 
	 * Stock deletedEntity = stockServiceImpl.fetchEntry(id);
	 * 
	 * Assert.assertNull("failure - expected null", deletedEntity);
	 * 
	 * }
	 */
	 

}
