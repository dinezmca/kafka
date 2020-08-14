package com.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kafka.api.request.PromotionRequest;
import com.kafka.broker.message.PromotionMessage;
import com.kafka.broker.producer.PromotionProducer;

@Component
public class PromotionAction {

	@Autowired
	private PromotionProducer producer;

	public void publishToKafka(PromotionRequest request) {
		PromotionMessage message = new PromotionMessage(request.getPromotionCode());

		producer.publish(message);
	}

}
