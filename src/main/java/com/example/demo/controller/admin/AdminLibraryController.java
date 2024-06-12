package com.example.demo.controller.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.entity.Users;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AdminLibraryController {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RentalsRepository rentalsRepository;
	@Autowired
	ItemsRepository itemsRepository;

	@GetMapping("/admin/rental")
	public String rental() {
		return "admin/rental";
	}

	@PostMapping("/admin/rental")
	public String rentalPost(
			@RequestParam(name = "id", defaultValue = "") Integer id,
			@RequestParam(name = "user_id", defaultValue = "") Integer user_id,
			Model model) {
		List<String> errorList = new ArrayList<String>();
		
		try {
			Items item = itemsRepository.findById(id).get();
		} catch (Exception e) {
			errorList.add("存在しない本のidです" + "<br>");
		}
		try {
			Users user = usersRepository.findById(id).get();
		}catch(Exception e) {
			errorList.add("存在しないユーザーidです" + "<br>");
		}
		
		try {
			Items item = itemsRepository.findById(id).get();
			Users user = usersRepository.findById(id).get();
		}catch(Exception e) {
			model.addAttribute("errorList", errorList);
			return "admin/rental";
		}
		
			LocalDate nowDate = LocalDate.now();
			LocalDate twoWeeksLater = nowDate.plusWeeks(2);
			Rentals rental = new Rentals(id, user_id, nowDate, twoWeeksLater);
			rentalsRepository.save(rental);
			return "admin/main";
		
	}
}