package com.example.demo.controller.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Categories;
import com.example.demo.entity.ItemTitle;
import com.example.demo.entity.Items;
import com.example.demo.entity.SubCategories;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ItemTitleRepository;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.SubCategoriesRepository;

@Controller
@RequestMapping("/admin/rentalcontrol/")
public class AdminItemController {

	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	ItemTitleRepository itemTitleRepository;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Autowired
	SubCategoriesRepository subCategoriesRepository;

	// 在庫管理画面を表示する
	@GetMapping("/inventory")
	public String inventory(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "sort", defaultValue = "") String sort,
			Model model) {

		List<ItemTitle> itemTitleList = null;
		// 資料名検索
		if (name.length() > 0) {
			// 資料テーブル一覧を取得(検索欄がある時)
			if (sort.equals("ASC")) {
				itemTitleList = itemTitleRepository.findByNameContainingOrderByPublicationDateAsc(name);
			} else if (sort.equals("DESC")) {
				itemTitleList = itemTitleRepository.findByNameContainingOrderByPublicationDateDesc(name);
			} else {
				itemTitleList = itemTitleRepository.findByNameContainingOrderByPublicationDateDesc(name);
			}
		} else {
			// 全資料テーブル一覧を取得(画面遷移後の初期表示 or 検索欄が空の時)
			if (sort.equals("ASC")) {
				itemTitleList = itemTitleRepository.findByOrderByPublicationDateAsc();
			} else if (sort.equals("DESC")) {
				itemTitleList = itemTitleRepository.findByOrderByPublicationDateDesc();
			} else {
				itemTitleList = itemTitleRepository.findAll();
			}
		}

		model.addAttribute("name", name);

		model.addAttribute("itemTitles", itemTitleList);
		// カテゴリー表示用
		List<Categories> categoryList = categoriesRepository.findAll();
		List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
		model.addAttribute("categories", categoryList);
		model.addAttribute("subCategories", subCategoryList);

		return "admin/inventory";

	}

	// 新規資料の追加処理を行う
	@PostMapping("/inventory")
	public String newItem(
			//			@RequestParam(name = "id", defaultValue = "") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "author", defaultValue = "") String author,
			@RequestParam(name = "publisher", defaultValue = "") String publisher,
			@RequestParam(name = "publicationDate", defaultValue = "") LocalDate publicationDate,
			@RequestParam(name = "summary", defaultValue = "") String summary,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "subCategoryId", defaultValue = "") Integer subCategoryId,
			RedirectAttributes model) {

		// エラーチェック
		List<String> errorList = new ArrayList<>();
		if (name.length() == 0) {
			errorList.add("資料名は必須です");
		}
		if (author.length() == 0) {
			errorList.add("作者名は必須です");
		}
		if (publisher.length() == 0) {
			errorList.add("出版社名は必須です");
		}
		if (summary.length() == 0) {
			errorList.add("あらすじ入力は必須です");
		}

		// エラー発生時は在庫一覧画面に戻す
		if (errorList.size() > 0) {
			model.addFlashAttribute("errorList", errorList);
			model.addFlashAttribute("name", name);
			model.addFlashAttribute("author", author);
			model.addFlashAttribute("publisher", publisher);
			model.addFlashAttribute("summary", summary);

			return "redirect:/admin/rentalcontrol/inventory";
		}
		
		ItemTitle itemTitle = new ItemTitle(name, author, publisher, publicationDate, summary, categoryId,
				subCategoryId);
		itemTitleRepository.save(itemTitle);
		
		model.addFlashAttribute("errorList", "追加しました");

		return "redirect:/admin/rentalcontrol/inventory";
	}

	// 本（タイトル）詳細編集画面を表示する
	@GetMapping("/inventory/detail/title/{id}")
	public String editTitle(
			@PathVariable("id") Integer id,
			Model model) {

		// ItemTitleテーブルを主キーで検索
		ItemTitle itemTitle = itemTitleRepository.findById(id).get();
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
		ItemTitle itemTitle = itemTitleRepository.findById(id).get();
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
		ItemTitle itemTitle = itemTitleRepository.findById(id).get();
		model.addAttribute("itemTitle", itemTitle);

		// テーブルでitemTitleIdを検索する
		List<Items> items = itemsRepository.findByItemTitleId(id);
		model.addAttribute("items", items);

		return "admin/editInventory";

	}

	// 本（在庫）の新規追加を行う
	@PostMapping("/inventory/detail")
	public String addBooks(
			@RequestParam(name = "id", defaultValue = "") Integer id,
			@RequestParam(name = "status", defaultValue = "") Integer status,
			@RequestParam(name = "arrivalDate", defaultValue = "") String arrivalDateS,
			@RequestParam(name = "memo", defaultValue = "") String memo,
			Model model) {

		LocalDate arrivalDate;
		try {
			arrivalDate = LocalDate.parse(arrivalDateS);
		} catch (DateTimeParseException e) {
			arrivalDate = LocalDate.now();
		}

		// itemsテーブルへの反映
		itemsRepository.save(new Items(id, status, arrivalDate, memo));

		//表示処理
		List<Items> items = itemsRepository.findByItemTitleId(id);
		model.addAttribute("items", items);
		ItemTitle itemTitle = itemTitleRepository.findById(id).get();
		model.addAttribute("itemTitle", itemTitle);
		return "admin/editInventory";
	}

	// 本（在庫）詳細編集画面を表示する
	@GetMapping("/inventory/detail/item/{id}")
	public String editItem(
			@PathVariable("id") Integer id,
			Model model) {

		// テーブルでitemTitleIdを検索する
		Items item = itemsRepository.findById(id).get();
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
		Items item = itemsRepository.findById(id).get();
		item.setStatus(status);
		item.setMemo(memo);

		// ItemTitleテーブルへの反映（UPDATE）
		itemsRepository.save(item);
		return "redirect:/admin/rentalcontrol/inventory/detail/" + item.getItemTitleId();
	}

}
