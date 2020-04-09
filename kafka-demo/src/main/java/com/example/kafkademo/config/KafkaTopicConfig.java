package com.example.kafkademo.config;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * create a topic
 */
@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${message.topic.name}")
	private String topicName;

	@Value(value = "${partitioned.topic.name}")
	private String partionedTopicName;

	@Value(value = "${filtered.topic.name}")
	private String filteredTopicName;

	@Value(value = "${greeting.topic.name}")
	private String greetingTopicName;

	public KafkaAdmin kafkaAdmin() {
		HashMap<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("baeldung", 1, (short)1);
	}
}
