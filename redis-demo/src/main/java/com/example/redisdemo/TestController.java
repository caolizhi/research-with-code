package com.example.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@GetMapping("/value")
	public String getValue() {
		String a = stringRedisTemplate.opsForValue().get("a");
		return a;
	}

}
