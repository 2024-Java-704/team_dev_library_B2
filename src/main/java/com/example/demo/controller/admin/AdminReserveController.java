package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Reservations;
import com.example.demo.repository.ReservationsRepository;

@Controller
@RequestMapping("/admin")
public class AdminReserveController {

	@Autowired
	ReservationsRepository reservationsRepository;

	// 予約一覧画面表示
	@GetMapping("/rentalcontrol/reserve")
	public String index(
			Model model) {

		// 全予約情報テーブル一覧を取得
		List<Reservations> reservationList = reservationsRepository.findByStatus(0);

		model.addAttribute("reservations", reservationList);

		return "admin/reserve";
	}

	// 予約取り置き画面表示
	@GetMapping("/rentalcontrol/reserved")
	public String reservedindex(
			Model model) {

		// 全資料テーブル一覧を取得
		List<Reservations> reservationList = reservationsRepository.findByStatusIn(new Integer[] {1,2,3});
		model.addAttribute("reservations", reservationList);

		return "admin/reserved";
	}

	// statusを変更する処理を行う
	@PostMapping("/rentalcontrol/reserved/{id}")
	public String reservedFinish(
			@PathVariable(name = "id") Integer id,
			Model model) {

		// Reservationオブジェクトの生成
		Reservations reservation = reservationsRepository.findById(id).get();
		reservation.setStatus(4);

		// reservationsテーブルの値を変更する
		reservationsRepository.save(reservation);

		return "redirect:/admin/rentalcontrol/reserved";
	}

}
