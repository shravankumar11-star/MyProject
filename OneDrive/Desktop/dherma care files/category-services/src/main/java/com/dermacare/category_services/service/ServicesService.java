package com.dermacare.category_services.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.dermacare.category_services.dto.ServiceDto;

public interface ServicesService {

	public ServiceDto addService(ServiceDto servicesDomain);

	public ServiceDto getServiceById(String serviceId);

	public List<ServiceDto> getServicesByCategoryId(String categoryId);

	public boolean checkServiceExistsAlready(String string, String serviceName);

	public List<ServiceDto> getAllService();

	public ServiceDto updateService(String serviceId, ServiceDto domainService);

	public void deleteServiceById(String serviceId);

	public void deleteServiceByCategoryId(ObjectId objectId);

}
