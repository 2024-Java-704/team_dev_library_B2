package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Items;

public interface ItemsRepository  extends JpaRepository< Items,Integer>{

}
