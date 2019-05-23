package com.evictory.inventorycloud.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.evictory.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.evictory.inventorycloud.modal.Stock;
import com.evictory.inventorycloud.modal.StockDetails;
import com.evictory.inventorycloud.service.StockService;

@RestController
@RequestMapping(value = "/stock")
public class StockController {
	
	@Autowired
	StockService stockService;
	
	public final String responseSuccess = "Success";
	public final String responseFailed = "Failed";
	public final String messageSuccessPOST = "Succesfully added into database.";
	public final String messageFailedPOST = "Failed to add values into database.";
	public final String messageSuccessGET = "Succesfully withdrawed from database.";
	public final String messageFailedGET = "Failed to withdraw from database.";
	public final String messageSuccessPUT = "Succesfully updated database.";
	public final String messageFailedPUT = "Failed to update database.";
	public final String messageSuccessDELETE = "Succesfully delete from database.";
	public final String messageFailedDELETE = "Failed to Delete from database.";
	
	
	
		@RequestMapping(value = "/openstock", method = RequestMethod.POST) // create  stock log with all its respective details
	    public ResponseEntity<?> saveAll(@Valid @RequestBody Stock stock)  {
			
			stock.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
	        if(stockService.saveAll(stock)) {
	        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"POST"));				
			}else {				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"POST"));
			}
	        
	    }
		
		
	    @RequestMapping(value = "/openstock", method = RequestMethod.GET) // fetch all stock logs with its respective stock details
	    public ResponseEntity<?> fetchAll() { 
	    	
	    	List<Stock> openStocks = stockService.fetchAll();
			if(openStocks == null || openStocks.size() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"GET"));
			}else {
				return ResponseEntity.ok(openStocks);			
			}
		}
	    
	 
	    @RequestMapping(value = "/openstock/entry", method = RequestMethod.POST)  // create a new stock log only
	    public ResponseEntity<?> saveEntry(@RequestBody Stock stock) {

	    	stock.setDate(ZonedDateTime.now(ZoneId.of("UTC-4")));
	        if(stockService.saveEntry(stock)) {
	        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"POST"));				
			}else {				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"POST"));
			}
	    }
	    
	 
	    @RequestMapping(value = "/openstock/entry/{id}", method = RequestMethod.PUT) // update existing stock details entry
	    public ResponseEntity<?> updateEntry(@PathVariable Integer id, @RequestBody Stock stock){ // open stock log id
	    	
	    	if(stockService.updateEntry(id, stock)) {
	        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"PUT"));				
			}else {				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"PUT"));
			}
	    }
	    
	    @RequestMapping(value = "/openstock/entry/{id}", method = RequestMethod.GET) // fetch a stock log by id
	    public ResponseEntity<?> fetchEntry(@PathVariable Integer id) {
	    	Stock stock = stockService.fetchEntry(id);
	    	if (stock == null) {
	    		throw new MessageBodyConstraintViolationException("Stock log entry not available.");
			}
	    	return ResponseEntity.status(HttpStatus.ACCEPTED).body(stock);
			
		}
	     
	    @RequestMapping(value = "/openstock/entry/{id}", method = RequestMethod.DELETE) // delete existing stock log with its details
	    public ResponseEntity<?> deleteEntry(@PathVariable Integer id){
	    	
	    	if(stockService.deleteEntry(id)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"DELETE"));
			}else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"DELETE"));
			}
	    	
	    }
		
	    // create a new open stock detail entry for an existing stock log 
	    @RequestMapping(value = "/openstock/details/{id}", method = RequestMethod.POST)
	    public ResponseEntity<?> saveDetails(@PathVariable Integer id, @RequestBody StockDetails stockDetails) {
	    	
	    	if(stockService.saveDetails(id, stockDetails)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"POST"));
			}else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"POST"));
			}
	    }
		
	    // update existing stock details entry
	    @RequestMapping(value = "/openstock/details/{sid}", method = RequestMethod.PUT)
	    public ResponseEntity<?>  updateDetails(@Valid @PathVariable String sid, @RequestBody StockDetails details){
	     	    int id;	
	    	if(!NumberUtils.isCreatable(sid)) {
	    		throw new RuntimeException( "ID should be an Interger");
	    	}else {
	    		id=Integer.valueOf(sid);
	    	}
	    	
	    	if(stockService.updateDetails(id, details)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"PUT"));
			}else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"PUT"));
			}
	    }
	    
	 // delete existing stock details entry
	    @RequestMapping(value = "/openstock/details/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?>  deleteDetails(@PathVariable Integer id){
	     
	    	
	    	if(stockService.deleteDetails(id)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"DELETE"));
			}else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"DELETE"));
			}
	    }
	    
	    // fetch all stock details by stock log by id
	    @RequestMapping(value = "/openstock/detailsAll/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> deleteAllDetails(@PathVariable Integer id) {

	    	if(stockService.deleteAllDetails(id)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(oncall(true,"DELETE"));
			}else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(oncall(false,"DELETE"));
			}
		}
	    
		public Response oncall(boolean ifsuccess, String type) {
			Response response = new Response();
			String messagefailed = "";
			String messagesuccess = "";
			switch (type) {
			case "POST":
				messagefailed = messageFailedPOST;
				messagesuccess = messageSuccessPOST;
				break;
			case "GET":
				messagefailed = messageFailedGET;
				messagesuccess = messageSuccessGET;
				break;
			case "PUT":
				messagefailed = messageFailedPUT;
				messagesuccess = messageSuccessPUT;
				break;
			case "DELETE":
				messagefailed = messageFailedDELETE;
				messagesuccess = messageSuccessDELETE;
				break;
			default:
				break;
			}
			if(ifsuccess) {
				response.setResponse(responseSuccess);
				response.setMessage(messagesuccess);
			}else {
				response.setResponse(responseFailed);
				response.setMessage(messagefailed);
			}
			
			return response;
		}

		class Response{
		   	 
	    	private String response;
	    	private String message;
			public String getResponse() {
				return response;
			}
			public void setResponse(String response) {
				this.response = response;
			}
			public String getMessage() {
				return message;
			}
			public void setMessage(String message) {
				this.message = message;
			}
	    	
	    	
	    }
}
