package com.cotsoft.demo01;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "greeting")
public class GreetingController {

	private final AtomicLong counter = new AtomicLong();
	private static final String template = "Hello %s";
	
	
	@GetMapping
	public Greeting saludar(@RequestParam(value = "name", defaultValue = "Word") String name)
	{
		return new Greeting( counter.incrementAndGet(), String.format(template, name));
	}
	
}
