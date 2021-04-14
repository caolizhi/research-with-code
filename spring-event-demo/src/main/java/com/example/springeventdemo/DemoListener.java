package com.example.springeventdemo;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DemoListener {

	@EventListener
	public void methodA(DemoEvent event) {
		log.info("event:" + event);
		log.info(event.getData().toString());
	}
}
