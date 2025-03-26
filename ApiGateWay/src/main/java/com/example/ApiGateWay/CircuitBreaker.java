package com.example.ApiGateWay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/controll")
public class CircuitBreaker {
	
	@GetMapping("/QuetionserviceFallBack")
	public String fallBack() {
		return "Quizservice is down";
	}

}
