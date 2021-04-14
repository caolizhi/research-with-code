package com.example.springeventdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import lombok.Getter;
import lombok.Setter;

/**
 *  custom a event
 * @param <T>
 */
public class DemoEvent<T> extends ApplicationContextEvent {

	@Setter
	@Getter
	private T data;

	public DemoEvent(ApplicationContext source) {
		super(source);
	}
}
