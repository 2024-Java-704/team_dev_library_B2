package com.example.demo.entity牧野;

import java.io.Serializable;
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
@Table(name = "reservations")
public class Reservation implements Serializable {

	// フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 主キー

	@Column(name = "item_title_id")
	private Integer itemTitleId;

	@Column(name = "item_id")
	private Integer itemId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "ordered_on")
	private LocalDate orderedOn;

	private Integer status;

	@ManyToOne
	@JoinColumn(name = "item_title_id", insertable = false, updatable = false)
	ItemTitle牧野 itemTitle;

	// コンストラクタ
	public Reservation() {

	}

	public Reservation(Integer id, Integer status) {
		this.id = id;
		this.status = status;
	}

	public Reservation(Integer id, Integer itemTitleId, Integer itemId, Integer userId, LocalDate orderedOn) {
		this.id = id;
		this.itemTitleId = itemTitleId;
		this.itemId = itemId;
		this.userId = userId;
		this.orderedOn = orderedOn;
	}

	public Reservation(Integer id, Integer itemTitleId, Integer itemId, Integer userId, LocalDate orderedOn,
			Integer status) {
		this.id = id;
		this.itemTitleId = itemTitleId;
		this.itemId = itemId;
		this.userId = userId;
		this.orderedOn = orderedOn;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemTitleId() {
		return itemTitleId;
	}

	public void setItemTitleId(Integer itemTitleId) {
		this.itemTitleId = itemTitleId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public LocalDate getOrderedOn() {
		return orderedOn;
	}

	public void setOrderedOn(LocalDate orderedOn) {
		this.orderedOn = orderedOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ItemTitle牧野 getItemTitle() {
		return itemTitle;
	}

	public String getItemTitleName() {
		if (itemTitle == null) {
			return "null";
		}
		return itemTitle.getName();
	}

}
