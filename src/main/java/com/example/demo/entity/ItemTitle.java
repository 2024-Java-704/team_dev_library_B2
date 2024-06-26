package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "item_titles")
public class ItemTitle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String author;

	private String image;

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

	@Transient
	private String ranking;

	public String getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking + "位";
	}

	public ItemTitle() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public ItemTitle(Integer id, String name, String author, String image, String publisher, LocalDate publicationDate,
			String summary, Integer categoryId, Integer subCategoryId, Integer rentalNumber) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.image = image;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.rentalNumber = rentalNumber;
	}

	public ItemTitle(String name, String author, String image, String publisher, LocalDate publicationDate,
			String summary, Integer categoryId, Integer subCategoryId) {
		this.name = name;
		this.author = author;
		this.image = image;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.rentalNumber = 0;
	}

	public ItemTitle(String name, String author, String publisher, LocalDate publicationDate, String summary,
			Integer categoryId, Integer subCategoryId) {
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.summary = summary;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.rentalNumber = 0;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getImage() {
		if (this.image == null || image.length() == 0) {
			return "/image/no_image.png";
		} else {
			return image;
		}
	}

	public String getPublisher() {
		return publisher;
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public String getSummary() {
		return summary;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public Integer getRentalNumber() {
		return rentalNumber;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public void setRentalNumber(Integer rentalNumber) {
		this.rentalNumber = rentalNumber;
	}

}
