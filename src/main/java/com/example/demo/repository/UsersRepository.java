package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Users;

public interface UsersRepository extends JpaRepository<Users ,Integer> {
	
	List<Users> findByStatus(Integer status);
	List<Users> findAllByOrderByIdAsc();
	List<Users> findByStatusOrderByIdAsc(Integer status);
	List<Users> findByEmailAndPassword(String email, String password);
}
