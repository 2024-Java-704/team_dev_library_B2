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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Calendars;
import com.example.demo.entity.Categories;
import com.example.demo.entity.ItemTitle;
import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.entity.Reservations;
import com.example.demo.entity.SubCategories;
import com.example.demo.model.Account;
import com.example.demo.repository.CalendarsRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ItemTitleRepository;
import com.example.demo.repository.ItemTitleRepositoryB;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;
import com.example.demo.repository.SubCategoriesRepository;

@Controller
public class LibraryController {
	@Autowired
	Account account;

	@Autowired
	ReservationsRepository reservationsRepository;

	@Autowired
	ItemTitleRepository itemTitlerepository;

	// AND検索機能用
	@Autowired
	ItemTitleRepositoryB itemTitleRepositoryB;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Autowired
	SubCategoriesRepository subCategoriesRepository;

	@Autowired
	RentalsRepository rentalsRepository;

	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	CalendarsRepository calendarsRepository;

	// 検索結果表示
	@GetMapping("/library/search")
	public String search(
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "author", defaultValue = "") String author,
			@RequestParam(value = "publisher", defaultValue = "") String publisher,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "subCategoryId", defaultValue = "") Integer subCategoryId,
			@RequestParam(value = "sort", defaultValue = "") String sort,
			Model model,
			RedirectAttributes redirectAttributes) {

		//上部簡易検索欄
		List<ItemTitle> itemList = null;

		if (keyword.length() > 0) {
			itemList = itemTitlerepository.findByNameContainingOrAuthorContainingOrPublisherContaining(keyword, keyword,
					keyword);

			model.addAttribute("keyword", keyword);
			// カテゴリー表示用
			List<Categories> categoryList = categoriesRepository.findAll();
			List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
			model.addAttribute("categories", categoryList);
			model.addAttribute("subCategories", subCategoryList);

			model.addAttribute("itemlist", itemList);

			return "search";
		}

		// 項目入力なしで検索をクリックした時
		if (keyword.length() == 0 || keyword == null) {
			if ((name.length() == 0 || name == null) && (author.length() == 0 || author == null)
					&& (publisher.length() == 0 || publisher == null)
					&& (categoryId == null || categoryId == 0)
					&& (subCategoryId == null || subCategoryId == 0)) {
				redirectAttributes.addFlashAttribute("errorList", "1つ以上の項目を入力してください");

				return "redirect:/library";
			}
		}

		// 入力項目があった時
		itemList = itemTitleRepositoryB.findByKeyword(keyword, name, author, publisher, categoryId, subCategoryId,
				sort);
		// カテゴリー表示用
		List<Categories> categoryList = categoriesRepository.findAll();
		List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
		model.addAttribute("categories", categoryList);
		model.addAttribute("subCategories", subCategoryList);
		//入力項目
		model.addAttribute("name", name);
		model.addAttribute("author", author);
		model.addAttribute("publisher", publisher);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("subCategoryId", subCategoryId);
		model.addAttribute("sort", sort);

		model.addAttribute("itemlist", itemList);
		return "search";
	}

	//search内から検索
	@GetMapping("/library/search/more")
	public String moreSearch(@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "author", defaultValue = "") String author,
			@RequestParam(value = "publisher", defaultValue = "") String publisher,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "subCategoryId", defaultValue = "") Integer subCategoryId,
			@RequestParam(value = "sort", defaultValue = "") String sort,
			Model model) {
		//上部簡易検索欄
		List<ItemTitle> itemList = null;

		if (keyword.length() > 0) {
			itemList = itemTitlerepository.findByNameContainingOrAuthorContainingOrPublisherContaining(keyword, keyword,
					keyword);

			model.addAttribute("keyword", keyword);
			// カテゴリー表示用
			List<Categories> categoryList = categoriesRepository.findAll();
			List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
			model.addAttribute("categories", categoryList);
			model.addAttribute("subCategories", subCategoryList);

			model.addAttribute("itemlist", itemList);

			return "search";
		}

		// 項目入力なしで検索をクリックした時
		if (keyword.length() == 0 || keyword == null) {
			if ((name.length() == 0 || name == null) && (author.length() == 0 || author == null)
					&& (publisher.length() == 0 || publisher == null)
					&& (categoryId == null || categoryId == 0)
					&& (subCategoryId == null || subCategoryId == 0)) {
				model.addAttribute("errorList", "1つ以上の項目を入力してください");

				// カテゴリー表示用
				List<Categories> categoryList = categoriesRepository.findAll();
				List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
				model.addAttribute("categories", categoryList);
				model.addAttribute("subCategories", subCategoryList);
				return "search";
			}
		}

		// 入力項目があった時
		itemList = itemTitleRepositoryB.findByKeyword(keyword, name, author, publisher, categoryId, subCategoryId,
				sort);
		// カテゴリー表示用
		List<Categories> categoryList = categoriesRepository.findAll();
		List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
		model.addAttribute("categories", categoryList);
		model.addAttribute("subCategories", subCategoryList);
		//入力項目
		model.addAttribute("name", name);
		model.addAttribute("author", author);
		model.addAttribute("publisher", publisher);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("subCategoryId", subCategoryId);
		model.addAttribute("sort", sort);

		model.addAttribute("itemlist", itemList);
		return "search";
	}

	// 資料の詳細画面表示
	@GetMapping("/library/search/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		ItemTitle itemtitle = itemTitlerepository.findById(id).get();

		Categories category = categoriesRepository.findById(itemtitle.getCategoryId()).get();

		SubCategories subCategory = subCategoriesRepository.findById(itemtitle.getSubCategoryId()).get();

		List<Items> items = itemsRepository.findByItemTitleIdAndStatus(id, 0);
		List<Items> itemsNot = itemsRepository.findByItemTitleIdAndStatusIn(id, new Integer[] { 0, 1, 2, 3 });

		model.addAttribute("item", itemtitle);
		model.addAttribute("category", category.getName());
		model.addAttribute("subCategory", subCategory.getName());
		model.addAttribute("itemNum", items.size());
		model.addAttribute("itemNumNot", itemsNot.size());
		return "detail";
	}

	//予約処理
	@PostMapping("/library/search/{id}/reserve")
	public String reserve(@PathVariable("id") Integer id, RedirectAttributes attr) {
		ItemTitle itemtitle = itemTitlerepository.findById(id).get();
		if ((itemsRepository.findByItemTitleIdAndStatus(id, 0)).size() == 0) {
			if(reservationsRepository.findByUserIdAndItemTitleIdAndStatusIn(account.getId(), itemtitle.getId(),new Integer[] {0,1,2}).size() == 0) {
				if (account.getAuthority() == 0) {
					LocalDate nowDate = LocalDate.now();
					Reservations reservation = new Reservations(itemtitle.getId(), account.getId(), nowDate);
					reservationsRepository.save(reservation);
					attr.addFlashAttribute("errorList", "予約しました");
				}
			}
		}
		return "redirect:/";
	}

	// ユーザメイン画面表示
	@GetMapping({ "/", "/library" })
	public String index(Model model) {
		// カテゴリー表示用
		List<Categories> categoryList = categoriesRepository.findAll();
		List<SubCategories> subCategoryList = subCategoriesRepository.findAll();
		List<ItemTitle> itemList = itemTitlerepository.findAllByOrderByRentalNumberDesc().stream().limit(10).toList();

		for (int i = 0; i < 10; i++) {
			itemList.get(i).setRanking(i + 1);
		}

		model.addAttribute("categories", categoryList);
		model.addAttribute("subCategories", subCategoryList);
		model.addAttribute("itemList", itemList);

		LocalDate currentDate = LocalDate.now();

		LocalDate first = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
		LocalDate last = (first.plusMonths(1)).minusDays(1);
		List<Calendars> closeDates = calendarsRepository.findByClosedDateBetween(first, last);
		model.addAttribute("closeDates", closeDates);

		// 新作本表示用(出版日から2か月以内表示)
		List<ItemTitle> newArrivals = itemTitlerepository
				.findByPublicationDateGreaterThanEqualOrderByPublicationDateDesc(currentDate.minusMonths(2));
		model.addAttribute("newArrivals", newArrivals);

		if (reservationsRepository.findByUserIdAndStatusIn(account.getId(), new Integer[] { 1 }).size() > 0) {
			model.addAttribute("tuuti", "tuuti");
		}
		if (account.getAuthority() == 1) {
			model.addAttribute("tuuti", "tuuti");
		}
		return "main";
	}

	// 取置ボタン押下後にstatusを変更する処理を行う
	@PostMapping("/library/mypage/reserved/{id}")
	public String reserved(
			@PathVariable("id") Integer id,
			Model model) {
		// Reservationオブジェクトの生成
		Reservations reservation = reservationsRepository.findById(id).get();
		// statusを2(受取待機)に変更してテーブルに保存
		reservation.setStatus(2);
		reservationsRepository.save(reservation);

		// Itemsオブジェクトの生成
		Items item = itemsRepository.findById(reservation.getItemId()).get();
		// statusを3(取置中)に変更してテーブルに保存
		item.setStatus(3);
		itemsRepository.save(item);

		return "redirect:/login/mypage";
	}

	// 取置キャンセルボタン押下後にstatusを変更する処理
	@PostMapping("/library/mypage/cancel/{id}")
	public String cancel(
			@PathVariable("id") Integer id,
			Model model) {
		// Reservationオブジェクトの生成、statusを3(返却待機)に変更
		Reservations reservation = reservationsRepository.findById(id).get();
		reservation.setStatus(3);
		reservationsRepository.save(reservation);

		// 該当資料のItemsオブジェクトを生成
		Items item = itemsRepository.findById(reservation.getItemId()).get();
		//予約確認
		List<Reservations> reservations = reservationsRepository
				.findByItemTitleIdAndStatusOrderByOrderedOn(item.getItemTitleId(), 0);
		if (reservations.size() > 0) {
			Reservations reserve = reservations.get(0);
			reserve.setItemId(reservation.getItemId());
			item.setStatus(2);
			reservationsRepository.save(reserve);
			itemsRepository.save(item);

		} else {
			item.setStatus(6);
			itemsRepository.save(item);
		}
		return "redirect:/login/mypage";
	}

	//紛失処理
	/*
	@PostMapping("/library/mypage/history")
	public String lost(Model model) {
		List<Rentals>rentalList = rentalsRepository.findAll();
		model.addAttribute("rentalList",rentalList);
		
		return "/admin";
	}
	*/
	@PostMapping("/library/mypage/history")
	public String lost(@RequestParam("rentalsId") Integer rentalsId) {
		Rentals rental = rentalsRepository.findById(rentalsId).get();
		if (rental == null) {
			return "redirect:/library/mypage/history";
		}
		Integer itemId = rental.getItemId();
		Items item = itemsRepository.findById(itemId).get();

		rental.setStatus(2);
		item.setStatus(5);

		rentalsRepository.save(rental);
		itemsRepository.save(item);

		return "redirect:/library/mypage/history";
	}

}
