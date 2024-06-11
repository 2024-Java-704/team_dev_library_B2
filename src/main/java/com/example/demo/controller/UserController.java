package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Rentals;
import com.example.demo.entity.Reservations;
import com.example.demo.model.Account;
import com.example.demo.repository.RentalsRepository;
import com.example.demo.repository.ReservationsRepository;

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

	@GetMapping({ "/login/mypage" })
	public String myPage(Model model) {
		List<Rentals> rentalList = rentalsRepository.findAll();
		model.addAttribute("rentalList", rentalList);

		LocalDate currentDate = LocalDate.now();
		List<Rentals> overList = rentalsRepository.findByStatusAndClosingDateBefore(0,currentDate);
		model.addAttribute("overList", overList);
		
		List<Reservations> reservationsList = reservationsRepository.findAll();
		model.addAttribute("reservationsList",reservationsList );
		
		return "admin/mypage";
	}
	
	
	}
	

