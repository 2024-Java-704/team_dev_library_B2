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
public class AccountController {
	
	
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
		
		//非延滞者権利処理
		List<Users> restrictedUsers = usersRepository.findByStatus(1);
		for(Users restUser:restrictedUsers) {
			List<Rentals> overDates = rentalsRepository.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(restUser.getId(), 0, LocalDate.now());
			if(overDates.size() == 0) {
				restUser.setStatus(0);
				usersRepository.save(restUser);
			}
		}
		
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
		
		model.addAttribute("itemTitleId",itemTitleId);
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
			model.addAttribute("itemTitleId",itemTitleId);
			return "loginReserve";
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
		return "redirect:" +("/library/search/"+ itemTitleId);
	}
	

	
}
