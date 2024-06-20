package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Account {
	private String name;
	private Integer id;
	private Integer authority;

	
	//コンストラクタ
		public Account() {
	}
	public Account(String name, Integer id) {
		this.name = name;
		this.id = id;
		this.authority=0;
	}
	
	public Account(String name, Integer id, Integer authority) {
		this.name = name;
		this.id = id;
		this.authority = authority;
	}
	//ゲッター・セッター
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuthority() {
		if(authority == null) {
			return 0;
		}
		return authority;
	}
	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	
	
	
}