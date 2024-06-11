package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLibraryController {

	// 返却画面を表示する
	@GetMapping("/admin/return")
	public String returnBook() {

		return "admin/return";
	}

}
