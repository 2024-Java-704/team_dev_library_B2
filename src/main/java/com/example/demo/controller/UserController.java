package com.example.demo.controller;

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

import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.entity.Reservations;
import com.example.demo.entity.Users;
import com.example.demo.model.Account;
import com.example.demo.repository.CalendarsRepository;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;
import com.example.demo.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	HttpSession session;

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
		// 入力項目チェック
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
			model.addAttribute("errorList", errorList);
			model.addAttribute("email", email);
			return "admin/login";
		}

		Users user = userList.get(0);
		// 管理者権限チェック
		if (user.getStatus() != 9) {
			errorList.add("管理者権限がありません");
			model.addAttribute("errorList", errorList);
			return "admin/login";
		}

		account.setId(user.getId());
		account.setName(user.getName());
		account.setAuthority(user.getStatus());

		//延滞者権利処理
		LocalDate nowDate = LocalDate.now();
		
		List<Rentals> overDate = rentalsRepository.findByClosingDateBeforeAndStatusIs(nowDate, 0);
		if(overDate != null) {
			for(Rentals rental:overDate) {
				Users overDateUser = rental.showUser();
				if(overDateUser.getStatus() == 0) {
					overDateUser.setStatus(1);
					usersRepository.save(overDateUser);
				}
			}
		}
		
		List<Users> overDateUsers = usersRepository.findByStatus(1);
		
		//延滞者の予約キャンセル処理
		if (overDateUsers != null) {
			for (Users overDateUser : overDateUsers) {
				//延滞者の予約一覧
				List<Reservations> reservations = reservationsRepository.findByUserIdAndStatusIn(overDateUser.getId(),new Integer[] { 0, 1, 2 });
				if (reservations != null) {
					for(Reservations reservation:reservations) {
						if(reservation.getStatus() == 1 || reservation.getStatus() == 2) {
							//取り置いている時
							//次の人
							List<Reservations> forFindNext = reservationsRepository.findByItemTitleIdAndStatusOrderByOrderedOn(reservation.getItemTitleid(), 0);
							Items item = itemsRepository.findById(reservation.getItemId()).get();
							if(forFindNext != null) {
								int i = 0;
								//次の人整理
								while(i <forFindNext.size()) {
									if(forFindNext.get(i).showUsersStatus() == 1) {
										forFindNext.remove(i);
									}else {
										i++;
									}
								}
							}
							
							if(forFindNext.size() > 0) {
								//次の人に渡す
								Reservations nextReserve = forFindNext.get(0);
								nextReserve.setItemId(item.getId());
								nextReserve.setStatus(1);
								reservationsRepository.save(nextReserve);
								
								item.setStatus(2);
								reservation.setStatus(4);
							}else {
								//次の人がいない
								item.setStatus(3);
								reservation.setStatus(3);
							}
							itemsRepository.save(item);
						}else {
							//取りおいていない
							reservation.setStatus(4);
							
						}
					}
					reservationsRepository.saveAll(reservations);
				}
			}
		}

		return "admin/main";
	}

	// 管理者ログアウト
	@GetMapping("/admin/logout")
	public String adminLogout() {
		session.invalidate();
		return "redirect:/admin/login";
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

		if (name.equals("") || address.equals("") || tel.equals("") || tel.length() != 11 || email.equals("")
				|| birthday == null || password.equals("") || emailCheck(email)) {
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

		//権利変更
		List<Rentals> overList = rentalsRepository
				.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(account.getId(), 0, LocalDate.now());
		if (overList.size() > 0) {
			if (user.getStatus() != 9) {
				user.setStatus(1);
				usersRepository.save(user);
			}
		}

		account.setId(user.getId());
		account.setName(user.getName());
		account.setAuthority(user.getStatus());
		return "redirect:/library";
	}

	//ログアウト処理
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	//本詳細画面からのユーザログイン処理
	// ユーザログイン画面表示
	@GetMapping("/library/search/{id}/login")
	public String loginReserve(@PathVariable("id")Integer itemTitleId,
								Model model) {
		return "loginReserve";
	}
	// ユーザログイン処理
	@PostMapping("/library/search/{id}/login")
	public String loginReservePost(@PathVariable("id")Integer itemTitleId,
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
			return "/library/search/"+itemTitleId+"/login";
		}

		// ログイン成功
		Users user = userList.get(0);

		//権利変更
		List<Rentals> overList = rentalsRepository
				.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(account.getId(), 0, LocalDate.now());
		if (overList.size() > 0) {
			if (user.getStatus() != 9) {
				user.setStatus(1);
				usersRepository.save(user);
			}
		}

		account.setId(user.getId());
		account.setName(user.getName());
		account.setAuthority(user.getStatus());
		return "redirect:/library/search/"+ itemTitleId;
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
