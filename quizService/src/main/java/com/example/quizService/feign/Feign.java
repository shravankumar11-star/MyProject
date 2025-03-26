package com.example.quizService.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.quizService.dto.Question;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;




//import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "QUESTIONSSERVICE")
@CircuitBreaker(name = "QUIZSERVICE", fallbackMethod  = "genericMethod" )
public interface Feign {
	  @GetMapping("/api/questions/quens/{id}")
	 public Optional<Question> getQuestionById(@PathVariable String id);
	  
	  @DeleteMapping("/{id}")
	    public String deleteQuestion(@PathVariable String id);

default void genericMethod(Exception ex) throws Exception{
throw new Exception(" server is down");
}
}
