package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Rentals;

public interface RentalsRepository  extends JpaRepository<Rentals ,Integer>{

}
