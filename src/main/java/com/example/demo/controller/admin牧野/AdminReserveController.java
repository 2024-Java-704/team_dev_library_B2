package com.example.demo.controller.admin牧野;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity牧野.Reservation;
import com.example.demo.repository牧野.ReservationRepository;

@Controller
@RequestMapping("/admin")
public class AdminReserveController {

	@Autowired
	ReservationRepository reservationRepository;

	// 予約一覧画面表示
	@GetMapping("/rentalcontrol/reserve")
	public String index(
			Model model) {

		// 全予約情報テーブル一覧を取得
		List<Reservation> reservationList = reservationRepository
				.findAll()
				.stream()
				.filter(reservation -> reservation.getStatus() != 4)
				.toList();

		model.addAttribute("reservations", reservationList);

		return "admin/reserve";
	}

	// 予約取り置き画面表示
	@GetMapping("/rentalcontrol/reserved")
	public String reservedindex(
			Model model) {

		// 全資料テーブル一覧を取得
		List<Reservation> reservationList = reservationRepository
				.findAll()
				.stream()
				.filter(reservation -> reservation.getStatus() != 4)
				.toList();

		model.addAttribute("reservations", reservationList);

		return "admin/reserved";
	}

	// statusを変更する処理を行う
	@PostMapping("/rentalcontrol/reserved/{id}")
	public String reservedFinish(
			@PathVariable(name = "id") Integer id,
			Model model) {

		// Reservationオブジェクトの生成
		Reservation reservation = reservationRepository.findById(id).get();
		reservation.setStatus(4);

		// reservationsテーブルの値を変更する
		reservationRepository.save(reservation);

		return "redirect:/admin/rentalcontrol/reserved";
	}

}
