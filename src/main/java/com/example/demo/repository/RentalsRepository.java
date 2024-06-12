package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Rentals;


public interface RentalsRepository  extends JpaRepository<Rentals,Integer>{

	List<Rentals> findByStatusAndClosingDateBefore(Integer status,LocalDate date);

	List<Rentals> findByClosingDateBeforeAndStatusIs(LocalDate nowDate,Integer status);
	//	List<Rentals> findByItemTitle(ItemTitle itemTitle);

	List<Rentals> findByUserIdAndItemIdAndStatus(Integer uid, Integer iid, Integer status);
	List<Rentals> findByUserIdAndItemIdAndStatusOrderByRentalDate(Integer uid, Integer iid, Integer status);
	
	List<Rentals> findByUserIdAndStatusAndClosingDateBeforeOrderByRentalDate(Integer userId,Integer status,LocalDate date);
	List<Rentals> findByUserIdAndStatusOrderByRentalDate(Integer userId,Integer status);
	
	List<Rentals> findByUserIdOrderByRentalDate(Integer userId);
	

}
