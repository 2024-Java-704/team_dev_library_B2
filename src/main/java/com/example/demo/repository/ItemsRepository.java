package com.example.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Items;

public interface ItemsRepository  extends JpaRepository< Items,Integer>{
	@Query(value = "SELECT MAX FROM items ORDER BY id ASC LIMIT 1",nativeQuery = true)
	Items findMax();
	
	List<Items> findByIdOrderByIdAsc(Integer Id);
}
