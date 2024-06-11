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

import com.example.demo.entity.Users;
import com.example.demo.model.Account;
import com.example.demo.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
	@Autowired
	HttpSession session;
	
	@Autowired
	Account account;
	
	@Autowired
	UsersRepository usersRepository;
	
	// 管理者ログイン画面を表示する
	@GetMapping("/admin/login")
	public String index() {
		return "admin/login";
	}
	
	// 管理者ログイン処理
	@PostMapping("/admin/login")
	public String adminPost(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		/* ここにメールアドレスやパスワード判定処理を書く */
		
		return "admin/main";
	}
	
	
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
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("tel") String tel,
			@RequestParam("email") String email,
			@RequestParam("birthday") LocalDate birthday,
			@RequestParam("password") String password,
			Model model) {
		Users user = new Users(name, address, tel, email, birthday, password);
		usersRepository.save(user);
		return "login";
	}
	
	// ユーザログイン画面表示
	@GetMapping("/login")
	public String loginIndex() {
		return "login";
	}
	
	// ユーザログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		// 入力されたメールアドレスとパスワードチェック
		List<String> errorList = new ArrayList<>();
		List<Users> userList = usersRepository.findByEmailAndPassword(email, password);
		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスを入力してください");
		}
		if (password == null || password.length() == 0) {
			errorList.add("パスワードを入力してください");
		}
		if ((email.length() != 0 && password.length() != 0) && (userList.size() == 0 || userList == null)) {
			errorList.add("メールアドレスとパスワードが一致しませんでした");
		}
		
		if (errorList.size() > 0) {
			// ログイン失敗
			model.addAttribute("errorList", errorList);
			model.addAttribute("email", email);
			return "login";
		}
		
		// ログイン成功
		Users user = userList.get(0);
		account.setId(user.getId());
		account.setName(user.getName());
		return "redirect:/library";
	}
	
	// ユーザマイページ表示
	@GetMapping({"/login/mypage"})
	public String myPage() {
		return "mypage";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
}
