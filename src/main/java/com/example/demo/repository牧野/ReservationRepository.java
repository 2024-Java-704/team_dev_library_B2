package com.example.demo.repository牧野;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity牧野.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findByStatus(Integer status);

}
