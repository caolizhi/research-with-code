package com.example.springeventdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SendEvent {
	@Autowired
	ApplicationContext applicationContext;

	// send a event
	public void send() {
		DemoEvent demoEvent = new DemoEvent<>(applicationContext);
		demoEvent.setData("information");
		applicationContext.publishEvent(demoEvent);
	}
}
