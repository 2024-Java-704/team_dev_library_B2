package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;

@Controller
@RequestMapping("/admin")
public class AdminRentalController {

	@Autowired
	RentalsRepository rentalsRepository;
	
	@Autowired
	ItemsRepository itemsRepository;

	// 貸出管理画面表示
	@GetMapping("/rentalcontrol")
	public String index() {

		return "admin/rentalsControl";
	}

	@GetMapping("/rentalcontrol/rentals")
	public String rentals(Model model) {
		List<Rentals> rentals = rentalsRepository.findByStatusInOrderByRentalDate(new Integer[] { 0, 2 });

		model.addAttribute("rentaling", rentals);
		return "admin/rentalControl";
	}
	
	// 紛失申告確認
	@PostMapping("/rentalcontrol/confirm/{id}")
	public String confirm(
			@PathVariable("id") Integer id,
			Model model) {
		// Rentalsオブジェクトの生成
		Rentals rental = rentalsRepository.findById(id).get();
		// statusを3(紛失確認済)に変更して保存
		rental.setStatus(3);
		rentalsRepository.save(rental);
		
		// Itemsオブジェクトの生成
		Items item = itemsRepository.findById(rental.getItemId()).get();
		// statusを4(利用不可)に変更して保存
		item.setStatus(4);
		itemsRepository.save(item);
		
		return "redirect:/admin/rentalcontrol/rentals";
	}
}
