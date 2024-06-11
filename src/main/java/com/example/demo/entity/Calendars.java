package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="calendars")
public class Calendars {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="closed_date")
	private LocalDate closedDate;
	
	@Column(name="date_detail")
	private String dateDetail;

	public Calendars() {
	}

	public Calendars(Integer id, LocalDate closedDate, String dateDetail) {
		this.id = id;
		this.closedDate = closedDate;
		this.dateDetail = dateDetail;
	}

	public Calendars(LocalDate closedDate, String dateDetail) {
		this.closedDate = closedDate;
		this.dateDetail = dateDetail;
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public String getDateDetail() {
		return dateDetail;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	public void setDateDetail(String dateDetail) {
		this.dateDetail = dateDetail;
	}
	
	
	
}
