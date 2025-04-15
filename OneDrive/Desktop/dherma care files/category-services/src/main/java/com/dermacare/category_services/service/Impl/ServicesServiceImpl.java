package com.dermacare.category_services.service.Impl;

import java.util.List;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dermacare.category_services.dto.ServiceDto;
import com.dermacare.category_services.entity.Services;
import com.dermacare.category_services.repository.ServicesRepository;
import com.dermacare.category_services.service.ServicesService;
import com.dermacare.category_services.util.HelperForConversion;

@Service
public class ServicesServiceImpl implements ServicesService {

	@Autowired
	ServicesRepository managmentRepository;
	
	

	private double CalculateDiscountAmount(byte discountpercentage, double price) {

		if (discountpercentage <= 0 || price <= 0) {
			return 0;
		}
		return price * (discountpercentage / 100.0);
	}

	private double CalculateTaxAmount(byte taxPercentage, double price) {
		if (taxPercentage <= 0 || price <= 0) {
			return 0;
		}
		return (taxPercentage / 100.0) * price;
	}

	private double calcualatePlatfomFee(byte  platformFeePercentage , double price) {
		
		if ( platformFeePercentage <= 0 || price <= 0) {
			return 0;
		}
		return ( platformFeePercentage / 100.0) * price;

	}
	
	public void  calculateAmounts(Services entity) {
		double discountAmount=CalculateDiscountAmount(entity.getDiscountPercentage(), entity.getPrice());
		double taxAmount=CalculateTaxAmount(entity.getTaxPercentage(),entity.getPrice());
		double platfromFee=calcualatePlatfomFee(entity.getPlatformFeePercentage(),entity.getPrice());
		entity.setDiscountAmount(discountAmount);
		entity.setTaxAmount(taxAmount);
		entity.setPlatformFee(platfromFee);
		entity.setDiscountedCost(entity.getPrice()-discountAmount);
		entity.setClinicPay(entity.getPrice()-platfromFee);
		entity.setFinalCost(entity.getPrice()-discountAmount+taxAmount);
	}

	public ServiceDto addService(ServiceDto dto) {
		
		Services service = HelperForConversion.toEntity(dto);
		calculateAmounts(service);
		Services savedService = managmentRepository.save(service);
		return  HelperForConversion.toDto(savedService);
	}

	public List<ServiceDto> getServicesByCategoryId(String categoryId) {

		List<Services> listOfService = managmentRepository.findByCategoryId(new ObjectId(categoryId));

		if (listOfService.isEmpty()) {
			return null;
		}
		return HelperForConversion.toDtos(listOfService);
	}

	public ServiceDto getServiceById(String serviceId) {
		Optional<Services> optionalService = managmentRepository.findById(new ObjectId(serviceId));
		if (optionalService.isEmpty()) {
			return null;
		}
		Services Service = optionalService.get();
		return HelperForConversion.toDto(Service);
	}

	public void deleteServiceById(String serviceId) {
		managmentRepository.deleteById(new ObjectId(serviceId));
	}

	public ServiceDto updateService(String serviceId, ServiceDto domainService) {
		Optional<Services> optionalService = managmentRepository.findById(new ObjectId(serviceId));
		Services Service = optionalService.get();

		if (Service == null) {
			throw new RuntimeException("Service not found with ID: " + serviceId);
		}

		Services toUpdate = HelperForConversion.toEntity(domainService);
		toUpdate.setServiceId(new ObjectId(serviceId));
		Services updService = managmentRepository.save(toUpdate);
		return HelperForConversion.toDto(updService);
	}

	public void deleteServiceByCategoryId(ObjectId objectId) {
		List<Services> listOfService = managmentRepository.findByCategoryId(objectId);
		if (!listOfService.isEmpty()) {
			managmentRepository.deleteAllByCategoryId(objectId);
		}
	}

	public List<ServiceDto> getAllService() {
		List<Services> listOfService = managmentRepository.findAll();
		if (listOfService.isEmpty()) {
			return null;
		}
		return HelperForConversion.toDtos(listOfService);
	}

	public void updateCategoryname(String categroyId, String categoryName) {
		List<Services> listOfService = managmentRepository.findByCategoryId(new ObjectId(categroyId));
		//List<Service> updatedService = HelperForConversion.updateService(listOfService, categoryName);
	    //managmentRepository.saveAll(updatedService);
	}

	public boolean checkServiceExistsAlready(String categoryId, String serviceName) {
		Optional<Services> optional = managmentRepository
				.findByCategoryIdAndServiceNameIgnoreCase(new ObjectId(categoryId), serviceName);
		if (optional.isPresent()) {
			return true;
		}
		return false;
	}

}
