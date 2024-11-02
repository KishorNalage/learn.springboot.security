package com.kishor.learn.spring.security.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SpringSecurityPlayController {
	
	@GetMapping(path = "/csrf-token")
	public CsrfToken getCsrtToken(HttpServletRequest request)
	{
		return (CsrfToken) request.getAttribute("_csrf");
	}

}
