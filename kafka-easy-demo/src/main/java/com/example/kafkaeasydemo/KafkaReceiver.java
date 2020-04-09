package com.example.kafkaeasydemo;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaReceiver {

	@KafkaListener(topics = {"caolizhi"})
	public void listen(ConsumerRecord<?,?> record) {
		Optional<?> kafkaMessag = Optional.ofNullable(record.value());
		if (kafkaMessag.isPresent()) {
			Object message = kafkaMessag.get();
			log.info("record = " + record);
			log.info("message = " + message);
		}
	}

}
