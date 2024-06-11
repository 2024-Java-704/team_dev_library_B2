package com.example.demo.entity牧野;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item implements Serializable {

	// フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 主キー

	@Column(name = "item_title_id")
	private Integer itemTitleId;

	private Integer status;

	@Column(name = "arrival_date")
	private Date arrivalDate;

	private String memo;

	// コンストラクタ
	public Item() {

	}

	public Item(Integer id, Integer itemTitleId, Integer status, Date arrivalDate, String memo) {
		this.id = id;
		this.itemTitleId = itemTitleId;
		this.status = status;
		this.arrivalDate = arrivalDate;
		this.memo = memo;
	}

	// ゲッター、セッター
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
