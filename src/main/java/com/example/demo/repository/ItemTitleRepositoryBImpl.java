package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ItemTitle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Component
public class ItemTitleRepositoryBImpl implements ItemTitleRepositoryB{
	
	@Autowired
	EntityManager em;
	
	// キーワードによる検索処理
	@Override
	public List<ItemTitle> findByKeyword(String keyword, String name, String author, String publisher, Integer categoryId, Integer subCategoryId) {
		
		// SQL
		String sql = "SELECT * FROM item_titles ";
		
		// フリーワード検索用
		if (keyword != null && keyword.length() > 0) {
			sql += "WHERE name LIKE :keyword";
			sql += " OR author LIKE :keyword";
			sql += " OR publisher LIKE :keyword";
		}
		
		// 詳細検索用
		int count = 0;
		if (name != null && name.length() > 0) {
			sql += "WHERE name LIKE :name";
			count++;
		}
		if (author != null && author.length() > 0) {
			if (count != 0) {
				sql += " AND ";
			} else {
				sql += "WHERE ";
			}
			sql += "author LIKE :author";
			count++;
		}
		if (publisher != null && publisher.length() > 0) {
			if (count != 0) {
				sql += " AND ";
			} else {
				sql += "WHERE ";
			}
			sql += "publisher LIKE :publisher";
			count++;
		}
		if (categoryId != null && categoryId != 0) {
			if (count != 0) {
				sql += " AND ";
			} else {
				sql += "WHERE ";
			}
			sql += "category_id = :categoryId";
			count++;
		}
		if (subCategoryId != null && subCategoryId != 0) {
			if (count != 0) {
				sql += " AND ";
			} else {
				sql += "WHERE ";
			}
			sql += "sub_category_id = :subCategoryId";
		}
		
		// パラメータ
		Query query = em.createNativeQuery(sql, ItemTitle.class);
		if (keyword != null && keyword.length() > 0) {
			query.setParameter("keyword", "%" + keyword + "%");
		}
		
		if (name != null && name.length() > 0) {
			query.setParameter("name", "%" + name + "%");
		}
		if (author != null && author.length() > 0) {
			query.setParameter("author", "%" + author + "%");
		}
		if (publisher != null && publisher.length() > 0) {
			query.setParameter("publisher", "%" + publisher + "%");
		}
		if (categoryId != null && categoryId != 0) {
			query.setParameter("categoryId", categoryId);
		}
		if (subCategoryId != null && subCategoryId != 0) {
			query.setParameter("subCategoryId", subCategoryId);
		}
			
		// SQL実行（参照系）
		@SuppressWarnings("unchecked")
		List<ItemTitle> itemTitleList = query.getResultList();
		
		return itemTitleList;
	}
}
