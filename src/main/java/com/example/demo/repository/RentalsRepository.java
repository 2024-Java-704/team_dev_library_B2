package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Rentals;

public interface RentalsRepository  extends JpaRepository<Rentals ,Integer>{
	List<Rentals> findByClosingDateBeforeAndStatusIs(LocalDate nowDate,Integer status);

}
