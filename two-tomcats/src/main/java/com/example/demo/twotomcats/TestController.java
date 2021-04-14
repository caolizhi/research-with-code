package com.example.demo.twotomcats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@GetMapping("/hello")
	public String test() {
		return "hello world !";
	}

}
