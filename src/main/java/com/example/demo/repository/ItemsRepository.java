package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Items;


public interface ItemsRepository  extends JpaRepository<Items,Integer>{
	List<Items> findByItemTitleId(Integer itemTitleId);

}
