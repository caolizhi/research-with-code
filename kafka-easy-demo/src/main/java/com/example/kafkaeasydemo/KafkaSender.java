package com.example.kafkaeasydemo;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class KafkaSender {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private Gson gson = new GsonBuilder().create();

	public void send() {
		Message message = new Message();
		message.setId(System.currentTimeMillis());
		message.setMsg(UUID.randomUUID().toString());
		message.setSendTime(new Date());
		kafkaTemplate.send("caolizhi", gson.toJson(message));
	}

}
