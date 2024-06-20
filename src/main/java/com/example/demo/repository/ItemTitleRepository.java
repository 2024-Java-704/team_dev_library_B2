package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ItemTitle;

public interface ItemTitleRepository extends JpaRepository<ItemTitle, Integer> {

	List<ItemTitle> findByNameContaining(String keyword);

	List<ItemTitle> findByNameContainingOrAuthorContainingOrPublisherContaining(String name, String author,
			String publisher);

	List<ItemTitle> findAllByOrderByRentalNumberDesc();

	// 在庫一覧検索時使用

	List<ItemTitle> findByOrderByPublicationDateAsc();

	List<ItemTitle> findByOrderByPublicationDateDesc();

	List<ItemTitle> findByNameContainingOrderByPublicationDateAsc(String name);

	List<ItemTitle> findByNameContainingOrderByPublicationDateDesc(String name);
	
	List<ItemTitle> findByPublicationDateGreaterThanEqualOrderByPublicationDateDesc(LocalDate date);
}
