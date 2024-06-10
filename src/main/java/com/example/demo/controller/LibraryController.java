package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ItemTitle;
import com.example.demo.repository.ItemTitleRepository;
import com.example.demo.repository.ItemTitleRepositoryB;

@Controller
public class LibraryController {
	
	@Autowired
	ItemTitleRepository itemtitlerepository;
	
	// AND検索機能用
	@Autowired
	ItemTitleRepositoryB itemTitleRepositoryB;

	// 検索結果表示
	@GetMapping("/library/search")
	public String search(
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "author", defaultValue = "") String author,
			@RequestParam(value = "publisher", defaultValue = "") String publisher,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "subCategoryId", defaultValue = "") Integer subCategoryId,
			Model model) {
		
		// 項目入力なしで検索をクリックした時
		if (keyword.length() == 0 || keyword == null) {
			if ((name.length() == 0 || name == null) && (author.length() == 0 || author == null)
					&& (publisher.length() == 0 || publisher == null)
					&& (categoryId == null || categoryId == 0)
					&& (subCategoryId == null || subCategoryId == 0)) {
				model.addAttribute("error", "1つ以上の項目を入力してください");
				return "main";
			}
		}
		
		// 入力項目があった時
		List<ItemTitle> itemlist = itemTitleRepositoryB.findByKeyword(keyword, name, author, publisher, categoryId, subCategoryId);
		model.addAttribute("itemlist", itemlist);
		return "search";
	}
	
	// 資料の詳細画面表示
	@GetMapping("/library/search/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		ItemTitle itemtitle =itemtitlerepository.findById(id).get();
		model.addAttribute("item",itemtitle);
		return "detail";
	}
	
	// ユーザメイン画面表示
	@GetMapping({"/", "/library"})
	public String index() {
		return "main";
	}

}
