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

import com.example.demo.entity.Items;
import com.example.demo.entity.Rentals;
import com.example.demo.entity.Reservations;
import com.example.demo.entity.Users;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AdminLibraryController {

	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RentalsRepository rentalsRepository;
	@Autowired
	ItemsRepository itemsRepository;
	@Autowired
	ReservationsRepository reservationsRepository;

	@GetMapping("/admin/rental")
	public String rental() {
		return "admin/rental";
	}

	@PostMapping("/admin/rental")
	public String rentalPost(
			@RequestParam(name = "id", defaultValue = "") Integer id,
			@RequestParam(name = "userId", defaultValue = "") Integer userId,
			Model model) {
		List<String> errorList = new ArrayList<String>();

		try {
			Items item = itemsRepository.findById(id).get();
		} catch (Exception e) {
			errorList.add("存在しない本のidです");
		}

		try {

			Users user = usersRepository.findById(userId).get();
		} catch (Exception e) {
			errorList.add("存在しないユーザーidです");
		}

		try {
			Items item = itemsRepository.findById(id).get();
			Users user = usersRepository.findById(userId).get();
		} catch (Exception e) {
			model.addAttribute("errorList", errorList);
			return "admin/rental";
		}

		Items item = itemsRepository.findById(id).get();
		if (item.getStatus() != 0) {
			errorList.add("この本は貸出できません");
			model.addAttribute("errorList", errorList);
			return "admin/rental";
		}
		
		
		
		//権限確認
		List<Rentals> overList = rentalsRepository.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(userId,0,LocalDate.now());
		if(overList.size()>0) {
			Users user = usersRepository.findById(userId).get();
			if(user.getStatus()!=9) {
				user.setStatus(1);
				usersRepository.save(user);
			}
			
			errorList.add("延滞中のため貸出が制限されています");
			model.addAttribute("errorList", errorList);
			return "admin/rental";
		}

		item.setStatus(1);
		itemsRepository.save(item);

		LocalDate nowDate = LocalDate.now();
		LocalDate twoWeeksLater = nowDate.plusWeeks(2);
		Rentals rental = new Rentals(id, userId, nowDate, twoWeeksLater);
		rentalsRepository.save(rental);
		return "admin/main";

	}

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

		
		List<Rentals> rentals = rentalsRepository.findByUserIdAndItemIdAndStatus(userId, itemId, 0);

		// 返却処理
		if (rentals.size() > 0) {
			Rentals rental = rentals.get(0);
			LocalDate now = LocalDate.now();
			rental.setReturnDate(now);
			rental.setStatus(1);

			// rentalsテーブルへの反映
			rentalsRepository.save(rental);
			
			//制限解除処理
			Users user = usersRepository.findById(userId).get();
			if (user.getStatus() == 1) {
				List<Rentals> overDate = rentalsRepository.findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(userId, 0, LocalDate.now());
				if (overDate == null || overDate.size() == 0) {
					user.setStatus(0);
					usersRepository.save(user);
				}
			}
			
			//返却反映
			Items item = itemsRepository.findById(itemId).get();
			//予約確認
			List<Reservations> reservations = reservationsRepository.findByItemTitleIdAndStatusOrderByOrderedOn(item.getItemTitleId(), 0);
			if(reservations.size() > 0) {
				Reservations reserve = reservations.get(0);
				reserve.setItemId(itemId);
				item.setStatus(2);
				reservationsRepository.save(reserve);
				itemsRepository.save(item);
				
			}else {
				item.setStatus(0);
				itemsRepository.save(item);
			}


		} else {
			// 指定したIDがrentalsテーブルに存在しない場合
			errorList.add("返却対象が見つかりません");
			model.addAttribute("errorList", errorList);
		}

		return "/admin/return";
	}

}
