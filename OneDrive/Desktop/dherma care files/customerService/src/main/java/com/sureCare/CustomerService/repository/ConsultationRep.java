package com.sureCare.CustomerService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sureCare.CustomerService.entity.ConsultationEntity;

public interface ConsultationRep extends MongoRepository<ConsultationEntity, String> {

	ConsultationEntity findByconsultationId(String id);
	
}
