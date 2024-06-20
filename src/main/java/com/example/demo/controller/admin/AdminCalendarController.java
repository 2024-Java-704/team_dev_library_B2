package com.example.demo.controller.admin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		return "admin/adminCalendar";
	}
	
	
	@PostMapping("/admin/calendar/add")
	public String add(@RequestParam(name="date", defaultValue="") String dateS,
						@RequestParam(name="dateDetail",defaultValue="") String dateDetail,
						RedirectAttributes attrs) {
		
		LocalDate closedDate;
		try {
			closedDate = LocalDate.parse(dateS);
		} catch (DateTimeParseException e) {
			closedDate = null;
		}
		List<String> errorList = new ArrayList<String>();
		//ここにエラー処理
		if(closedDate == null) {
			errorList.add("日付を入力してください");
		}
		
		if(calendarsRepository.findByClosedDate(closedDate).size() > 0) {
			errorList.add("日付が重複しています");
		}
		if(errorList.size() > 0) {
			attrs.addFlashAttribute("errorList", errorList);
			return "redirect:/admin/calendar";
		}
		calendarsRepository.save(new Calendars(closedDate,dateDetail));
		attrs.addFlashAttribute("errorList", "追加しました");
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
