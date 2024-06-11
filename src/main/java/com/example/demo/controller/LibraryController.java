package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ItemTitle;
import com.example.demo.entity.Reservations;
import com.example.demo.model.Account;
import com.example.demo.repository.ItemTitleRepository;
import com.example.demo.repository.ReservationsRepository;

@Controller
public class LibraryController {
	
	@Autowired
	ReservationsRepository reservationsRepository;

	@Autowired
	ItemTitleRepository itemtitlerepository;

	@Autowired
	Account account;
	
	private String mypage;

	@GetMapping("/library/search")
	public String search(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			Model model) {

		List<ItemTitle> itemlist = null;

		if (keyword.length() > 0) {
			itemlist = itemtitlerepository.findByNameContaining(keyword);
			model.addAttribute("itemlist", itemlist);

			return "search";
		}
		return "search";
	}

	@GetMapping("/library/search/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		ItemTitle itemtitle = itemtitlerepository.findById(id).get();
		model.addAttribute("item", itemtitle);
		return "detail";
	}

	//予約処理
	@PostMapping("/library/search/{id}/reserve")
	public String reserve(@PathVariable("id") Integer id, Model model) {
		ItemTitle itemtitle = itemtitlerepository.findById(id).get();
		 LocalDate nowDate = LocalDate.now();
		Reservations reservation = new Reservations(itemtitle.getId(),account.getId(),nowDate,0);
		reservationsRepository.save(reservation);

		return "main";
	}

	// ユーザメイン画面表示
	@GetMapping({ "/", "/library" })
	public String index() {
		return "main";
	}

}
