package com.AdminService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.AdminService.entity.Admin;



@Repository
public interface AdminRepository extends MongoRepository<Admin, Long> {

	@Query("{ 'userName': ?0, 'password': ?1 }")
	Optional<Admin> findByUsernameAndPassword(String userName, String password);
}