package com.kafka.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.broker.message.OrderMessage;

@Service
public class OrderListener {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);
	
	@KafkaListener(topics = "t.commodity.order")
	public void listen(OrderMessage orderMessage) {
		int totalItemamount = orderMessage.getPrice() * orderMessage.getQuantity();
		logger.info("Processing order {}, item {}, credit  card number {}, total amount for this Item {}",
				
				orderMessage.getOrderNumber(), orderMessage.getItemName(), orderMessage.getCreditCardNumber(), totalItemamount);
	}

}
