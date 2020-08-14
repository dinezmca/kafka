package com.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafka.api.request.OrderRequest;
import com.kafka.command.action.OrderAction;
import com.kafka.entity.Order;

@Service
public class OrderService {
	
	@Autowired
	private OrderAction action;
	

	public String saveOrder(OrderRequest request) {
		
		//1.Convert OrderRequest to order
		
		Order order = action.convertToOrder(request);
		
		//2. Save order to Database
		
		action.saveToDatabase(order);
		
		//3. Flatten the item & order as a kafka message , and publish
		order.getItems().forEach(action::publishToKafka);
		
		//4 return the order number 
		return order.getOrderNumber();
	}

}
