package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ItemTitle;

public interface ItemTitleRepository extends JpaRepository<ItemTitle, Integer> {

	List<ItemTitle> findByNameContaining(String keyword);
	List<ItemTitle> findByNameContainingOrAuthorContainingOrPublisherContaining(String name,String author,String publisher);

}
