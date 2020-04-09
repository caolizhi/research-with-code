package com.example.kafkademo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Greeting {

	private String msg;
	private String name;

	@Override
	public String toString() {
		return msg + "," + name + "!";
	}
}
