package com.example.demo.entity牧野;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_titles")
public class ItemTitle牧野 implements Serializable {

	// フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 主キー

	private String name;

	private String author;

	//	private Image image; // java.awtで良いか確認

	private String publisher;

	@Column(name = "publication_date")
	private LocalDate publicationDate;

	private String summary;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "sub_category_id")
	private Integer subCategoryId;

	@Column(name = "rental_number")
	private Integer rentalNumber;

	// コンストラクタ
	public ItemTitle牧野() {

	}

	public ItemTitle牧野(Integer id, String name, String summary) {
		this.id = id;
		this.name = name;
		this.summary = summary;
	}

	public ItemTitle牧野(Integer id, String name, String author, String publisher, LocalDate publicationDate,
			String summary) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
	}

	public ItemTitle牧野(Integer id, String name, String author, String publisher, LocalDate publicationDate,
			String summary, Integer categoryId, Integer subCategoryId, Integer rentalNumber) {
		// Image imageが通用するか確認
		this.id = id;
		this.name = name;
		this.author = author;
		//		this.image = image;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.rentalNumber = rentalNumber;
	}

	// ゲッター、セッター
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Integer getRentalNumber() {
		return rentalNumber;
	}

	public void setRentalNumber(Integer rentalNumber) {
		this.rentalNumber = rentalNumber;
	}

}
