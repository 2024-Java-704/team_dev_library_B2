package com.example.demo.controller.admin牧野;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminRentalController牧野 {

	// 貸出管理画面表示
	@GetMapping("/rentalcontrol")
	public String index() {
		return "admin/usercontroll";
	}

}
