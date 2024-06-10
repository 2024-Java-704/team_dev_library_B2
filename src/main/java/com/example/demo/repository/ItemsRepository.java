package com.example.demo.repository;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Items;

public interface ItemsRepository  extends JpaRepository< Items,Integer>{

	static List<Item> findByNameContaining(String keyword) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
