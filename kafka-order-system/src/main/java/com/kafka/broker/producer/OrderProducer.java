package com.kafka.broker.producer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.kafka.broker.message.OrderMessage;

@Service
public class OrderProducer {

	private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

	@Autowired
	private KafkaTemplate<String, OrderMessage> template;
	
	private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
		int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;

		List<Header> headers = new ArrayList<>();
		RecordHeader surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
		headers.add(surpriseBonusHeader);

		return new ProducerRecord<String, OrderMessage>("t.commodity.order", null, message.getOrderNumber(), message,
				headers);
	}

	public void publish(OrderMessage message) {
		ProducerRecord<String, OrderMessage> producerRecord = buildProducerRecord(message);
		template.send(producerRecord)
				.addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {

					@Override
					public void onFailure(Throwable ex) {
						logger.error("Order {} , item {} failed to publish , because {}", message.getOrderNumber(),
								message.getItemName(), ex.getMessage());

					}

					@Override
					public void onSuccess(SendResult<String, OrderMessage> result) {

						logger.info("Order {} , item {} published sucessfully", message.getOrderNumber(),
								message.getItemName());
					}
				});
		logger.info("Just dummy message Order {} , item {} published sucessfully to ensure aynsc process", message.getOrderNumber(),
				message.getItemName());
	}
}
