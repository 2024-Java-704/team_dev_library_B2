package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservations;

public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {

	List<Reservations> findByStatus(Integer status);

	List<Reservations> findByStatusIn(Integer[] status);

	List<Reservations> findByUserIdAndStatusIn(Integer userId, Integer[] status);

	List<Reservations> findByItemTitleIdAndStatusOrderByOrderedOn(Integer itemTitleId, Integer status);

	//	List<Reservations> findByItemTitleNameContaining(String keyword);

	//	List<ItemTitle> findByNameContaining(String keyword);

}
