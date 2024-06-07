package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ItemTitle;

public interface ItemTitleRepository  extends JpaRepository<ItemTitle ,Integer>{

}
