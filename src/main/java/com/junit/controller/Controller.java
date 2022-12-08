package com.junit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Controller 
{
	@GetMapping("/")
	public String test()
	{
		return "working";
	}
	@GetMapping("/sum")
	public String calculator()
	{
		return "working";
	}
}
