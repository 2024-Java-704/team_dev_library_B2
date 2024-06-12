package com.example.demo.controller.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Rentals;
import com.example.demo.entity.Users;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AdminUserController {
	
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RentalsRepository rentalsRepository;
	
	@GetMapping("")
	public String main() {
		return "admin/main";
	}
	
	@GetMapping("/admin/usercontrol")
	public String index() {
		return "admin/userControl";
	}

	@GetMapping("/admin/usercontrol/newuser")
	public String add() {
		return "admin/newUser";
	}
	
	
	@PostMapping("/admin/usercontrol/newuser")
	public String addPost(
			@RequestParam(name = "name",defaultValue = "")String name,
			@RequestParam(name = "address",defaultValue = "")String address,
			@RequestParam(name = "tel",defaultValue = "")String tel,
			@RequestParam(name = "email",defaultValue = "")String email,
			@RequestParam(name = "birthday",defaultValue = "")LocalDate birthday,
			@RequestParam(name = "password",defaultValue = "")String password,
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
			return "admin/newUser";
			
		}else {
			
		Users user = new Users(name,address,tel,email,birthday,password);
		usersRepository.save(user);
		return "admin/userControl";
		}
	}
	
	
	@GetMapping("/admin/usercontrol/user")
	public String user(Model model) {
		List<Users> userList = usersRepository.findAllByOrderByIdAsc();
		model.addAttribute("users", userList);
		return "admin/user";
	}
	
	@GetMapping("/admin/usercontrol/user/detail/{id}")
	public String editUser(
			@PathVariable("id") Integer id,
			Model model) {
		Users user = usersRepository.findById(id).get();
		model.addAttribute("user", user);
		return "admin/detail";
	}
	
	@PostMapping("/admin/usercontrol/user/detail/{id}")
	public String editUserPost(
			@RequestParam(name = "id",defaultValue = "")Integer id,
			@RequestParam(name = "name",defaultValue = "")String name,
			@RequestParam(name = "address",defaultValue = "")String address,
			@RequestParam(name = "tel",defaultValue = "")String tel,
			@RequestParam(name = "email",defaultValue = "")String email,
			@RequestParam(name = "birthday",defaultValue = "")LocalDate birthday,
			@RequestParam(name = "password",defaultValue = "")String password,
			@RequestParam(name = "joinDate",defaultValue = "")LocalDate joinDate,
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
			
			Users user = usersRepository.findById(id).get();
			model.addAttribute("errorList",errorList);
			model.addAttribute("user", user);
			return "admin/detail";
			
		}else {
		
		
		
		Users user = new Users(id,name,address,tel,email,birthday,password,joinDate);
		usersRepository.save(user);
		return "redirect:/admin/usercontrol/user";
		}
	}
	
	@GetMapping("/admin/usercontrol/overdueuser")
	public String overdue(Model model) {
		LocalDate ldt = LocalDate.now();
		List<Rentals> rentalList= rentalsRepository.findByClosingDateBeforeAndStatusIs(ldt,0);
		
		for(Rentals r:rentalList) {
			Users user = usersRepository.findById(r.getUserId()).get();
			user.setStatus(1);
			usersRepository.save(user);
		}
		List<Users> userList = usersRepository.findByStatusOrderByIdAsc(1);
		model.addAttribute("users",userList);
		return "admin/overDueuser";
	}
	
	
	
	
	@PostMapping("/admin/usercontrol/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
				usersRepository.deleteById(id);
		return "redirect:/admin/usercontrol/user";
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
