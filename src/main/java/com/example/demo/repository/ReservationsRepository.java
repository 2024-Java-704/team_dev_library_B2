package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservations;

public interface ReservationsRepository extends JpaRepository<Reservations ,Integer> {
	
	List<Reservations> findByStatus(Integer status);
}
