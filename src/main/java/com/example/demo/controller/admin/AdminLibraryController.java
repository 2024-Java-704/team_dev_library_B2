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

import com.example.demo.entity.Rentals;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AdminLibraryController {

	@Autowired
	RentalsRepository rentalsRepository;

	@Autowired
	UsersRepository usersRepository;

	// 返却画面を表示する
	@GetMapping("/admin/return")
	public String returnBook(Model model) {
		model.addAttribute("userId", "");
		model.addAttribute("itemId", "");

		return "admin/return";
	}

	// 返却処理を実行する
	@PostMapping("/admin/return")
	public String returnBookPost(
			@RequestParam(name = "userId", defaultValue = "") Integer userId,
			@RequestParam(name = "itemId", defaultValue = "") Integer itemId,
			Model model) {

		// エラーチェック
		List<String> errorList = new ArrayList<String>();
		if (userId == null) {
			errorList.add("ユーザIDを入力してください");
		}
		if (itemId == null) {
			errorList.add("資料IDを入力してください");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("userId", userId);
			model.addAttribute("itemId", itemId);
			return "/admin/return";
		}

		// ID存在チェック
		List<Rentals> rentals = rentalsRepository.findByUserIdAndItemIdAndStatus(userId, itemId, 0);

		// 返却処理
		if (rentals.size() > 0) {
			Rentals rental = rentals.get(0);
			LocalDate now = LocalDate.now();
			rental.setReturnDate(now);
			rental.setStatus(1);

			// rentalsテーブルへの反映
			rentalsRepository.save(rental);

		} else {
			// 指定したIDがrentalsテーブルに存在しない場合
			model.addAttribute("message", "返却対象が見つかりません");
		}

		return "/admin/return";
	}

}
