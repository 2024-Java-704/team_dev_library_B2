package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepository;

@Controller
public class UserController {
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
}