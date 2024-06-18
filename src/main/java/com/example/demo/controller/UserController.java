package com.example.demo.controller;

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
import com.example.demo.entity.Reservations;
import com.example.demo.entity.Users;
import com.example.demo.model.Account;
import com.example.demo.repository.CalendarsRepository;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class UserController {

	@Autowired
	Account account;

	@Autowired
	RentalsRepository rentalsRepository;

	@Autowired
	ReservationsRepository reservationsRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	CalendarsRepository calendarsRepository;
	@Autowired
	ItemsRepository itemsRepository;

	/*  以下ユーザ関連の処理  */

	// 新規会員登録画面表示
	/* 会員が自分で行う登録画面(パッケージtemplates内)を表示しています */
	@GetMapping("/login/newuser")
	public String add() {
		return "addUser";
	}

	// 会員登録処理
	@PostMapping("/login/newuser")
	public String addPost(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "birthday", defaultValue = "") LocalDate birthday,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		List<String> errorList = new ArrayList<String>();

		if (name.equals("") || address.equals("") || tel.equals("") || tel.length() != 11 || email.equals("")
				|| birthday == null || birthday.isAfter(LocalDate.now()) || password.equals("") || emailCheck(email)) {
			if (name.equals("")) {
				errorList.add("名前は必須です");
			}
			if (address.equals("")) {
				errorList.add("住所は必須です");
			}
			if (tel.equals("")) {
				errorList.add("電話番号は必須です");
			}
			if (tel.length() != 11) {
				errorList.add("電話番号はハイフン抜きの11桁で入力してください");
			}
			if (email.equals("")) {
				errorList.add("メールアドレスは必須です");
			}
			if (emailCheck(email)) {
				errorList.add("登録済みのメールアドレスです");
			}
			if (birthday == null) {
				errorList.add("生年月日は必須です");
			}
			if (birthday.isAfter(LocalDate.now())) {
				errorList.add("生年月日は今日以前を選択してください");
			}
			if (password.equals("")) {
				errorList.add("パスワードは必須です");
			}
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("birthday", birthday);
			model.addAttribute("errorList", errorList);
			return "addUser";
		}

		// 登録成功
		Users user = new Users(name, address, tel, email, birthday, password);
		usersRepository.save(user);
		return "login";
	}

	// ユーザマイページ表示
	@GetMapping({ "/login/mypage" })
	public String myPage(Model model) {
		//貸出中
		List<Rentals> rentalList = rentalsRepository.findByUserIdAndStatusOrderByRentalDate(account.getId(), 0);
		model.addAttribute("rentalList", rentalList);

		LocalDate currentDate = LocalDate.now();

		//延滞中
		List<Rentals> overList = rentalsRepository
				.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(account.getId(), 0, currentDate);
		model.addAttribute("overList", overList);

		//予約中
		List<Reservations> reservationsList = reservationsRepository.findByUserIdAndStatusIn(account.getId(),
				new Integer[] { 0, 1, 2 });
		model.addAttribute("reservationsList", reservationsList);

		return "mypage";
	}

	//履歴表示用
	@GetMapping("/library/mypage/history")
	public String history(Model model) {

		List<Rentals> rentalHistory = rentalsRepository.findByUserIdOrderByRentalDateDesc(account.getId());
		model.addAttribute("rentalHistory", rentalHistory);
		return "history";
	}

	//email重複確認用関数 trueならば存在する
	private boolean emailCheck(String el) {
		List<Users> users = usersRepository.findAll();
		boolean check = false;
		for (Users c : users) {
			if (c.getEmail().equals(el)) {
				check = true;
				break;
			}
		}
		return check;
	}
}
