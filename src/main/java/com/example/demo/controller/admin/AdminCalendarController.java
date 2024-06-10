package com.example.demo.controller.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Calendars;
import com.example.demo.repository.CalendarsRepository;

@Controller
public class AdminCalendarController {
	
	@Autowired
	CalendarsRepository calendarsRepository;
	
	@GetMapping("/admin/calendar")
	public String index(Model model) {
		
		LocalDate monthFirst = LocalDate.now().withDayOfMonth(1);
		List<Calendars> calendars = calendarsRepository.findByClosedDateGreaterThanEqualOrderByClosedDate(monthFirst);
		
		
		model.addAttribute("calendars", calendars);
		return "admin/admincalendar";
	}
	
	
	@PostMapping("/admin/calendar/add")
	public String add(@RequestParam(name="date", defaultValue="") String dateS,
						@RequestParam(name="dateDetail",defaultValue="") String dateDetail
						) {
		
		LocalDate closedDate;
		try {
			closedDate = LocalDate.parse(dateS);
		} catch (DateTimeParseException e) {
			closedDate = null;
		}
		
		//ここにエラー処理
		if(closedDate == null) {
			return "redirect:/admin/calendar";
		}
		
		if(calendarsRepository.findByClosedDate(closedDate).size() > 0) {
			return "redirect:/admin/calendar";
		}
		
		
		calendarsRepository.save(new Calendars(closedDate,dateDetail));
		
		return "redirect:/admin/calendar";
	}
	
	@PostMapping("/admin/calendar/delete")
	public String delete(@RequestParam(name="date", defaultValue="") String dateS) {
		
		LocalDate closedDate;
		try {
			closedDate = LocalDate.parse(dateS);
		} catch (DateTimeParseException e) {
			closedDate = null;
		}
		
		//ここにエラー処理
		if(closedDate == null) {
			return "redirect:/admin/calendar";
		}
		
		calendarsRepository.deleteAll(calendarsRepository.findByClosedDate(closedDate));
		
		return "redirect:/admin/calendar";
	}


}
