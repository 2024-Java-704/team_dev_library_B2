package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rentals")
public class Rentals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="item_id")
	private Integer itemId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="rental_date")
	private LocalDate rentalDate;
	
	@Column(name="return_date")
	private LocalDate returnDate;
	
	@Column(name="closing_date")
	private LocalDate closingDate;
	
	private Integer status;
	/* 0:貸出中
	 * 1:返却済み
	 * 2:紛失申請中
	 * 3:紛失確認済
	 * */
	
	@ManyToOne
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	Items items;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable = false, updatable = false)
	Users users;

	public Rentals() {
	}

	public Rentals(Integer userId, Integer itemId) {
		this.userId = userId;
		this.itemId = itemId;
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
		this.status = 1;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getStatusStr() {
		switch(this.status) {
			case 0 : return "貸出中";
			case 1 : return "返却済";
			case 2 : return "紛失申告中";
			case 3 : return "紛失確認済";
			default : return "";
		}
	}
	public String getItemTitleName() {
		return items.getItemTitleName();
	}
	public String getUserName() {
		return users.getName();
	}
	public Integer getUserStatus() {
		return users.getStatus();
	}
	
	
	public Users showUser() {
		return users;
	}
	

}
