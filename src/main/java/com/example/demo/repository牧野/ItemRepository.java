package com.example.demo.repository牧野;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity牧野.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findByItemTitleId(Integer itemTitleId);

}
