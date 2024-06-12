package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Calendars;

public interface CalendarsRepository extends JpaRepository<Calendars,Integer>{
	List<Calendars> findByClosedDate(LocalDate closedDate);
	List<Calendars> findByClosedDateGreaterThanEqualOrderByClosedDate(LocalDate date);
	
	List<Calendars> findByClosedDateBetween(LocalDate gessi, LocalDate getumatu);
}
