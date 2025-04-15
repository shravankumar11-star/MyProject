package com.sureCare.CustomerService.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Import Optional
import com.sureCare.CustomerService.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {
	
	public Optional<Customer> findByMobileNumber(long mobileNumber);

	public Optional<Customer> findByEmailId(String emailId);

	public List<Customer> findByfullName(String fullName);
	

}