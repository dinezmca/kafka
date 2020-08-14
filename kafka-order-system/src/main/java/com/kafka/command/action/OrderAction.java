package com.kafka.command.action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kafka.api.request.OrderItemRequest;
import com.kafka.api.request.OrderRequest;
import com.kafka.broker.message.OrderMessage;
import com.kafka.broker.producer.OrderProducer;
import com.kafka.entity.Order;
import com.kafka.entity.OrderItem;
import com.kafka.repository.OrderItemRepository;
import com.kafka.repository.OrderRepository;

@Component
public class OrderAction {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProducer producer;

	public Order convertToOrder(OrderRequest request) {
		Order order = new Order();
		order.setCreditCardNumber(request.getCreditCardNumber());
		order.setOrderLocation(request.getOrderLocation());
		order.setOrderDateTime(LocalDateTime.now());
		order.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());
		
		List<OrderItem> items  = request.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
		items.forEach(item->item.setOrder(order));
		order.setItems(items);
		return order;
	}
	
	public OrderItem convertToOrderItem(OrderItemRequest orderItem) {
		OrderItem item = new OrderItem();
		item.setPrice(orderItem.getPrice());
		item.setQuantity(orderItem.getQuantity());
		item.setItemName(orderItem.getItemName());
		return item;
		
	}

	public void publishToKafka(OrderItem item) {
		OrderMessage orderMessage = new OrderMessage();

		orderMessage.setItemName(item.getItemName());
		orderMessage.setPrice(item.getPrice());
		orderMessage.setQuantity(item.getQuantity());

		orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
		orderMessage.setOrderLocation(item.getOrder().getOrderLocation());
		orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
		orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());

		producer.publish(orderMessage);
	}
	
	public void saveToDatabase(Order order) {
		orderRepository.save(order);
		order.getItems().forEach(orderItemRepository::save);
		//order.getItems().stream().map(orderItemRepository::save);
		
	}

}
