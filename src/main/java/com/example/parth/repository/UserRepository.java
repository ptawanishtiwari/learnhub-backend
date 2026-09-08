package com.example.parth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.parth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	

}
