package com.kafka.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.broker.message.PromotionMessage;

@Service
public class PromotionUppercaseListener {
	
	private static final Logger log = LoggerFactory.getLogger(PromotionUppercaseListener.class);
	
	@KafkaListener(topics = "t.commodity.promotion-uppercase")
	public void listener(PromotionMessage message) {
		log.info("Promotion message {}",message.getPromotionCode() );

	}
}
