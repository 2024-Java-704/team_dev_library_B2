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
@Table(name ="reservations")
public class Reservations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="item_title_id")
	private Integer itemTitleid;
	
	@Column(name="item_id")
	private Integer itemId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="ordered_on")
	private LocalDate orderedOn;
	
	private Integer status;
	//0:予約中
	//1:予約待機
	//2:受取待機
	//3:返却待機
	//4:終了
	
	@ManyToOne
	@JoinColumn(name = "item_title_id", insertable = false, updatable = false)
	ItemTitle itemTitle;

	public Reservations() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	

	public Reservations(Integer id, Integer itemTitleid, Integer itemId, Integer userId, LocalDate orderedOn,
			Integer status) {
		this.id = id;
		this.itemTitleid = itemTitleid;
		this.itemId = itemId;
		this.userId = userId;
		this.orderedOn = orderedOn;
		this.status = status;
	}
	
	public Reservations(Integer itemTitleid, Integer userId, LocalDate orderedOn, Integer status) {
		this.itemTitleid = itemTitleid;
		this.userId = userId;
		this.orderedOn = orderedOn;
		this.status = status;
	}
	public Reservations(Integer itemTitleid, Integer userId, LocalDate orderedOn) {
		this.itemTitleid = itemTitleid;
		this.userId = userId;
		this.orderedOn = orderedOn;
		this.status = 0;
	}



	public Integer getId() {
		return id;
	}

	public Integer getItemTitleid() {
		return itemTitleid;
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getUserId() {
		return userId;
	}

	public LocalDate getOrderedOn() {
		return orderedOn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItemTitleid(Integer itemTitleid) {
		this.itemTitleid = itemTitleid;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
		this.status = 1;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setOrderedOn(LocalDate orderedOn) {
		this.orderedOn = orderedOn;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public ItemTitle getItemTitle() {
		return itemTitle;
	}

	public String getItemTitleName() {
		if (itemTitle == null) {
			return "null";
		}
		return itemTitle.getName();
	}
	public String getStatusStr() {
		switch(status) {
		case 0: return "予約中";
		case 1: return "予約待機";
		case 2: return "受取待機";
		case 3: return "返却待機";
		case 4: return "終了";
		default: return "";
		//0:予約中
		//1:予約待機
		//2:受取待機
		//3:返却待機
		//4:終了
		}
	}

}
