package com.kafka.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.broker.message.OrderReplyMessage;

@Service
public class OrderReplyListener {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderReplyListener.class);
	
	@KafkaListener(topics = "t.commodity.order-reply")
	public void listener(OrderReplyMessage replyMessage) {
		logger.info("Reply messaage recieved : {}", replyMessage);
	}

}
