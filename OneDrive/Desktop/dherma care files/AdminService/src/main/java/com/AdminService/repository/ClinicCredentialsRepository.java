package com.AdminService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.AdminService.entity.ClinicCredentials;

@Repository
public interface ClinicCredentialsRepository extends MongoRepository<ClinicCredentials, String> {
    // Custom query to find by hospitalId
    ClinicCredentials findByUserName(String name);
    ClinicCredentials findByUserNameAndPassword(String name,String password);

}

