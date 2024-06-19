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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.entity.Reservations;
import com.example.demo.entity.Users;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AdminUserController {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RentalsRepository rentalsRepository;
	@Autowired
	ItemsRepository itemsRepository;
	@Autowired
	ReservationsRepository reservationsRepository;

	@GetMapping("/main")
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
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "birthday", defaultValue = "") LocalDate birthday,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		List<String> errorList = new ArrayList<String>();


			if (name.equals("")) {
				errorList.add("名前は必須です");
			}
			if (address.equals("")) {
				errorList.add("住所は必須です");
			}
			if (tel.equals("")) {
				errorList.add("電話番号は必須です");
			}
			if (tel.length() > 11) {
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
			if(errorList.size()>0) {
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("birthday", birthday);
			model.addAttribute("errorList", errorList);
			return "admin/newUser";
			}

			LocalDate joinDate = LocalDate.now();
			Users user = new Users(name, address, tel, email, birthday, password, joinDate);
			usersRepository.save(user);
			return "admin/userControl";

	}

	// 会員一覧表示
	@GetMapping("/admin/usercontrol/user")
	public String user(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {
		List<Users> userList = null;
		// 検索
		if (name.length() != 0) {
			userList = usersRepository.findByNameContaining(name);
			model.addAttribute("users", userList);
			model.addAttribute("name", name);
			return "admin/user";
		}

		// 全会員情報取得（画面遷移後初期表示 or 検索欄空欄）
		userList = usersRepository.findAllByOrderByIdAsc();
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
			@PathVariable(name = "id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "birthday", defaultValue = "") LocalDate birthday,
			@RequestParam(name = "password", defaultValue = "") String password,
			@RequestParam(name = "joinDate", defaultValue = "") LocalDate joinDate,
			Model model) {

		Users user = usersRepository.findById(id).get();
		//エラーチェック
		List<String> errorList = new ArrayList<String>();

		if (name.equals("")) {
			errorList.add("名前は必須です");
		}
		if (address.equals("")) {
			errorList.add("住所は必須です");
		}
		if (tel.equals("")) {
			errorList.add("電話番号は必須です");
		}
		if (tel.length() > 11) {
			errorList.add("電話番号はハイフン抜きの11桁で入力してください");
		}
		if (email.equals("")) {
			errorList.add("メールアドレスは必須です");
		}
		if (!email.equals(user.getEmail())) {
			if (emailCheck(email)) {
				errorList.add("登録済みのメールアドレスです");
			}
		}
		if (birthday == null) {
			errorList.add("生年月日は必須です");
		}
		if (password.equals("")) {
			errorList.add("パスワードは必須です" + "<br>");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("user", user);
			return "admin/detail";
		}

		//値のセット
		user.setName(name);
		user.setAddress(address);
		user.setTel(tel);
		user.setEmail(email);
		user.setBirthday(birthday);
		user.setPassword(password);

		usersRepository.save(user);

		return "redirect:/admin/usercontrol/user";

	}

	// 延滞者一覧表示
	@GetMapping("/admin/usercontrol/overdueuser")
	public String overdue(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {
		/*
		LocalDate ldt = LocalDate.now();
		List<Rentals> rentalList= rentalsRepository.findByClosingDateBeforeAndStatusIs(ldt,0);
		
		for(Rentals r:rentalList) {
			Users user = usersRepository.findById(r.getUserId()).get();
			user.setStatus(1);
			usersRepository.save(user);
		}
		*/
		List<Users> userList = null;
		// 検索
		if (name.length() != 0) {
			userList = usersRepository.findByNameContaining(name);
			model.addAttribute("users", userList);
			model.addAttribute("name", name);
			return "admin/overDueuser";
		}

		// 全延滞者一覧表示
		userList = usersRepository.findByStatusOrderByIdAsc(1);
		model.addAttribute("users", userList);
		return "admin/overDueuser";
	}

	@PostMapping("/admin/usercontrol/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes attr) {
		List<String> errorList = new ArrayList<String>();
		Users user =  usersRepository.findById(id).get();
		if(user != null){
			if(user.getStatus() != 9) {
				List<Rentals> rentals = rentalsRepository.findByUserIdAndStatusOrderByRentalDate(user.getId(), 0);
				if(rentals.size() > 0) {
					for(Rentals r : rentals) {
						r.setStatus(3);
						Items item = itemsRepository.findById(r.getItemId()).get();
						item.setStatus(4);
						item.addMemo("id:"+id+"の退会処理により紛失");
						itemsRepository.save(item);
					}
					rentalsRepository.saveAll(rentals);
					errorList.add("貸し出し中の資料を紛失扱いにしました");
				}
				List<Reservations> reservations = reservationsRepository.findByUserIdAndStatusIn(id, new Integer[]{0,1,2});
				if(reservations.size()>0) {
					for(Reservations r:reservations) {
						if(r.getStatus() == 0) {
							r.setStatus(4);
						}else {
							Items item = itemsRepository.findById(r.getItemId()).get();
							item.setStatus(6);
							itemsRepository.save(item);
							r.setStatus(3);
						}
					}
					reservationsRepository.saveAll(reservations);
					errorList.add("予約をキャンセルしました");
				}
				user.setName("退会ユーザ");
				user.setAddress("-");
				user.setTel("-");
				user.setEmail(user.getId().toString());
				user.setBirthday(LocalDate.parse("0001-01-01"));
				user.setStatus(2);
				user.setPassword(user.getId().toString());
				usersRepository.save(user);
				
			}else {errorList.add("管理者です");}
		}
		
		attr.addFlashAttribute("errorList", errorList);
		return "redirect:/admin/usercontrol/user";
	}

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
