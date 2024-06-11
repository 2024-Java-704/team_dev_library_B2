package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String address;
	private String tel;
	private String email;
	private LocalDate birthday;
	private String password;
	@Column(name="join_date")
	private LocalDate joinDate;
	private Integer status;
	//0:利用可　1:利用不可　9:管理者
	public Users() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public Users(Integer id, String name, String address, String tel, String email, LocalDate birthday, String password,
			LocalDate joinDate) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
		this.joinDate = joinDate;
		this.status = 0;
	}
	public Users(String name, String address, String tel, String email, LocalDate birthday, String password,
			LocalDate joinDate) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
		this.joinDate = joinDate;
		this.status = 0;
	}
	public Users(String name, String address, String tel, String email, LocalDate birthday, String password) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
		this.joinDate = LocalDate.now();
		this.status = 0;
	}
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getTel() {
		return tel;
	}
	public String getEmail() {
		return email;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public String getPassword() {
		return password;
	}
	public LocalDate getJoinDate() {
		return joinDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
