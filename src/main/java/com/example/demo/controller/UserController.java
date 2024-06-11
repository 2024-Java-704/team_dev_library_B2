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
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "birthday", defaultValue = "") LocalDate birthday,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		List<String> errorList = new ArrayList<String>();
		
		if(name.equals("") ||address.equals("") ||tel.equals("") ||tel.length()!=11 ||email.equals("") ||birthday== null||password.equals("") ||emailCheck(email)){
			if(name.equals("")){
				errorList.add("名前は必須です" + "<br>");}
			if(address.equals("")) {
				errorList.add("住所は必須です" + "<br>");}
			if(tel.equals("")) {
				errorList.add("電話番号は必須です" + "<br>");}
			if(tel.length()!=11) {
				errorList.add("電話番号はハイフン抜きの11桁で入力してください" + "<br>");}
			if(email.equals("")) {
				errorList.add("メールアドレスは必須です" + "<br>");}
			if (emailCheck(email)) {
				errorList.add("登録済みのメールアドレスです" + "<br>");}
			if(birthday==null) {
				errorList.add("生年月日は必須です" + "<br>");}
			if(password.equals("")) {
				errorList.add("パスワードは必須です" + "<br>");}
			model.addAttribute("name",name);
			model.addAttribute("address",address);
			model.addAttribute("tel",tel);
			model.addAttribute("email",email);
			model.addAttribute("birthday",birthday);
			model.addAttribute("errorList",errorList);
			return "addUser";
		}
		
		// 登録成功
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
	
	private boolean emailCheck(String el) {
		List<Users> users = usersRepository.findAll();
		boolean check = false;
		for(Users c:users) {
			if(c.getEmail().equals(el)) {
				check = true;
				break;
			}
		}
		return check;
	}
}
