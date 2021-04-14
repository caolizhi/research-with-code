package com.example.bluegreen;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BlueGreenController {

	@RequestMapping("/blue-green")
	public String getAddrAndPort(HttpServletRequest servletRequest) {
		int port = servletRequest.getLocalPort();
		switch (port) {
			case 8080:
				return "blue";
			case 9090:
				return "green";
			default: return "none";
		}
	}

	@GetMapping("/green")
	public String blue() {
		return "green";
	}

}
