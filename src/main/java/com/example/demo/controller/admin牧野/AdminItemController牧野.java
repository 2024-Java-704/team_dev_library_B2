package com.example.demo.controller.admin牧野;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity牧野.Item;
import com.example.demo.entity牧野.ItemTitle牧野;
import com.example.demo.repository牧野.ItemRepository;
import com.example.demo.repository牧野.ItemTitleRepository牧野;

@Controller
@RequestMapping("/admin/rentalcontrol/")
public class AdminItemController牧野 {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemTitleRepository牧野 itemTitleRepository;

	// 在庫管理画面を表示する
	@GetMapping("/inventory")
	public String inventory(
			Model model) {

		// 全資料テーブル一覧を取得
		List<ItemTitle牧野> itemTitleList = itemTitleRepository.findAll();
		model.addAttribute("itemTitles", itemTitleList);

		return "admin/inventory";
	}

	// 本（タイトル）詳細編集画面を表示する
	@GetMapping("/inventory/detail/title/{id}")
	public String editTitle(
			@PathVariable("id") Integer id,
			Model model) {

		// ItemTitleテーブルを主キーで検索
		ItemTitle牧野 itemTitle = itemTitleRepository.findById(id).get();
		model.addAttribute("itemTitle", itemTitle);

		// editItem.htmlを出力
		return "admin/editTitle";
	}

	// 本（タイトル）編集処理を実行する
	@PostMapping("/inventory/detail/title/{id}")
	public String editTitlePost(
			@PathVariable("id") Integer id,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "summary", defaultValue = "") String summary,
			Model model) {

		// ItemTitleオブジェクトの生成
		ItemTitle牧野 itemTitle = itemTitleRepository.findById(id).get();
		itemTitle.setName(name);
		itemTitle.setSummary(summary);

		// ItemTitleテーブルへの反映（UPDATE）
		itemTitleRepository.save(itemTitle);
		return "redirect:/admin/rentalcontrol/inventory";
	}

	// 本（在庫）詳細画面を表示する
	@GetMapping("/inventory/detail/{id}")
	public String books(
			@PathVariable("id") Integer id,
			Model model) {

		// 主キーで検索する
		ItemTitle牧野 itemTitle = itemTitleRepository.findById(id).get();
		model.addAttribute("itemTitle", itemTitle);

		// テーブルでitemTitleIdを検索する
		List<Item> items = itemRepository.findByItemTitleId(id);
		model.addAttribute("items", items);

		return "admin/editInventory";

	}

	// 本（在庫）詳細編集画面を表示する
	@GetMapping("/inventory/detail/item/{id}")
	public String editItem(
			@PathVariable("id") Integer id,
			Model model) {

		// テーブルでitemTitleIdを検索する
		Item item = itemRepository.findById(id).get();
		model.addAttribute("item", item);

		return "admin/editInventoryDetail";

	}

	// 本（在庫）詳細編集処理を実行する
	@PostMapping("/inventory/detail/item/{id}")
	public String editItemPost(
			@PathVariable("id") Integer id,
			@RequestParam(value = "status", defaultValue = "") Integer status,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {

		// ItemTitleオブジェクトの生成
		//ItemTitle itemTitle = new ItemTitle(id, name, summary);
		Item item = itemRepository.findById(id).get();
		item.setStatus(status);
		item.setMemo(memo);

		// ItemTitleテーブルへの反映（UPDATE）
		itemRepository.save(item);
		return "redirect:/admin/rentalcontrol/inventory/detail/" + item.getItemTitleId();
	}

}
