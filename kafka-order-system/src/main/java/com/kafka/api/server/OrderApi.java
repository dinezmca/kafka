package com.kafka.api.server;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.api.request.OrderRequest;
import com.kafka.api.response.OrderResponse;
import com.kafka.command.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
	
	@Autowired
	private OrderService service;
	
	@PostMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){
		
		//1.save order using service
		
		String orderNumber = service.saveOrder(request);
		
		//2.Create response 
		
		OrderResponse response = new OrderResponse(orderNumber);
		
		//3 Return response
		
		
		return ResponseEntity.ok(response);
	}

}


