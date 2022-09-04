package com.sree.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping
	public String findAll() {
		return "Hello All Users";
	}
	
	@GetMapping(value = "/{id}")
    public String findById(@PathVariable("id") Long id) {
        return "Hello Sree with Id";
    }
}
