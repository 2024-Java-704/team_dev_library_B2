package com.example.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.RentalsRepository;

@Controller
@RequestMapping("/admin")
public class AdminRentalController {

	@Autowired
	RentalsRepository rentalsRepository;

	// 貸出管理画面表示
	@GetMapping("/rentalcontrol")
	public String index(Model model) {
		/*
		List<Rentals> rentals = rentalsRepository.findByStatusInOrderByRentalDate(new Integer[] {0,2});
		
		model.addAttribute("rentaling",rentals);
		return "admin/rentalControl";
		*/

		return "admin/rentalsControll";
	}

}
