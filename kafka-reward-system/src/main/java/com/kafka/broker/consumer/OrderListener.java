package com.kafka.broker.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.broker.message.OrderMessage;

//@Service
public class OrderListener {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);
	
	@KafkaListener(topics = "t.commodity.order")
	public void listen(ConsumerRecord<String, OrderMessage> consumer) {
		Headers header =consumer.headers();
		OrderMessage orderMessage = consumer.value();
		logger.info("Processing order {}, item {}, credit card number {}", orderMessage.getOrderNumber(),
				orderMessage.getItemName(), orderMessage.getCreditCardNumber());
		logger.info("Headers are :");
		header.forEach(h-> logger.info("Key {}, value {}", h.key(), new String(h.value())));
		
		Double bonusPercentage = Double.parseDouble(new String(header.lastHeader("surpriseBonus").value()));
		Double bonusAmount = (bonusPercentage/100) * orderMessage.getPrice() * orderMessage.getQuantity();
		logger.info("Surprise bonus is {}", bonusAmount);
		
	}

}
