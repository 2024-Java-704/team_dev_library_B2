package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Account;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@GetMapping({"/login/mypage"})
	public String myPage() {
		
		return "mypage";
	}
}
