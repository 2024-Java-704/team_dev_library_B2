package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="sub_categories")
public class SubCategories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="category_id")
	private Integer categoryId;
	
	private String name;
	

	public SubCategories() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public SubCategories(Integer id, Integer categoryId, String name) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
	}
	public SubCategories(Integer categoryId, String name) {
		this.categoryId = categoryId;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}
	
}
