package com.example.demo.controller;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.repository.ItemsRepository;

@Controller
public class LibraryController {
	
	@Autowired
	ItemsRepository itemsrepository;
	
	private String mypage;

	@GetMapping("/library/search")
	public String search(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			Model model) {
		
		List<Item>Itemlist = null;
		
		if(keyword.length()>0) {
			Itemlist = (List<Item>) ItemsRepository.findByNameContaining(keyword);
					
		return "search";
		}
		return "search";
	}
	
	@GetMapping("/library/search/{id}")
	public String detail() {
		return "detail";
	}



	// ユーザメイン画面表示
	@GetMapping({"/", "/library"})
	public String index() {
		return "main";
	}

}
