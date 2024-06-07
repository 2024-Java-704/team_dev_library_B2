package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="rentals")
public class Rentals {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="item_id")
	Integer itemId;
	
	@Column(name="user_id")
	Integer userId;
	
	@Column(name="rental_date")
	LocalDate rentalDate;
	
	@Column(name="return_date")
	LocalDate returnDate;
	
	@Column(name="closing_date")
	LocalDate closingDate;
	
	Integer status;

	public Rentals() {
	}

	public Rentals(Integer id, Integer itemId, Integer userId, LocalDate rentalDate, LocalDate returnDate,
			LocalDate closingDate, Integer status) {
		this.id = id;
		this.itemId = itemId;
		this.userId = userId;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.closingDate = closingDate;
		this.status = status;
	}

	public Rentals(Integer itemId, Integer userId, LocalDate rentalDate, LocalDate returnDate, LocalDate closingDate,
			Integer status) {
		this.itemId = itemId;
		this.userId = userId;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.closingDate = closingDate;
		this.status = status;
	}

	public Rentals(Integer itemId, Integer userId, LocalDate rentalDate, LocalDate closingDate) {
		this.itemId = itemId;
		this.userId = userId;
		this.rentalDate = rentalDate;
		this.closingDate = closingDate;
		this.status = 0;
	}

	public Integer getId() {
		return id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}