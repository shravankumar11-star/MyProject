package com.orderService.controller;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BaseDomain.dto.Order;
import com.BaseDomain.dto.OrderEvent;
import com.orderService.kafka.OrderProducer;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	private OrderProducer orderProducer ;
	
	
	@PostMapping("/order")
	public String placeOrder(@RequestBody Order order) {
		order.setOrderId(Uuid.randomUuid().toString());
		OrderEvent orderevnt = new OrderEvent();
		orderevnt.setStatus("pending");
		orderevnt.setMessage("order is in pending state");
		orderevnt.setOrder(order);	
		orderProducer.sendMessage(orderevnt);
		return "order placed successfully";
	}
	
	
	

}
