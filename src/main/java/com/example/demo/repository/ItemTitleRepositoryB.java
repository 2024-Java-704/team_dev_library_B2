package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.ItemTitle;

@Repository
public interface ItemTitleRepositoryB {
	// 	キーワードによる検索処理
	List<ItemTitle> findByKeyword(String keyword, String name, String author, String publisher, Integer categoryId, Integer subCategoryId);
}
