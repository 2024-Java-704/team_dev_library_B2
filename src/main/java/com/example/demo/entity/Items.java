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
@Table(name="items")
public class Items {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "item_title_id")
	private Integer itemTitleId;
	
	private Integer status;
	/*
	 * 0:貸し出し可(在架)
	 * 1:貸し出し中
	 * 2:取り置き待ち(予約対象、取り置き承認待機)
	 * 3:取り置き中
	 * 4:利用不可(紛失、破損等)
	 * */
	
	@Column(name = "arrival_date")
	private LocalDate arrivalDate;
	
	private String memo;
	
	@ManyToOne
	@JoinColumn(name = "item_title_id", insertable = false, updatable = false)
	ItemTitle itemTitle;

	public Items() {
	}
	

	public Items(Integer id, Integer itemTitleId, Integer status, LocalDate arrivalDate, String memo) {
		this.id = id;
		this.itemTitleId = itemTitleId;
		this.status = status;
		this.arrivalDate = arrivalDate;
		this.memo = memo;
	}
	
	


	public Items(Integer itemTitleId, Integer status, LocalDate arrivalDate, String memo) {
		this.itemTitleId = itemTitleId;
		this.status = status;
		this.arrivalDate = arrivalDate;
		this.memo = memo;
	}


	public Integer getId() {
		return id;
	}

	public Integer getItemTitleId() {
		return itemTitleId;
	}

	public Integer getStatus() {
		return status;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItemTitleId(Integer itemTitleId) {
		this.itemTitleId = itemTitleId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	public String getItemTitleName() {
		return itemTitle.getName();
	}
	
}
