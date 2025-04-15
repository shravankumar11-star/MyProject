package com.dermacare.category_services.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dermacare.category_services.dto.ServiceDto;
import com.dermacare.category_services.service.ServicesService;
import com.dermacare.category_services.util.ResponseStructure;

@RestController
@RequestMapping("/v1/services")
public class ServiceController {

	@Autowired
	private ServicesService service;

	@PostMapping("/addService")
	public ResponseEntity<ResponseStructure<ServiceDto>> addService(@RequestBody ServiceDto dto) {

		boolean present = service.checkServiceExistsAlready(dto.getCategoryId(), dto.getServiceName());

		if (present) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(null, "Service is Already Present in Same Category",
							HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ServiceDto entitySaved = service.addService(dto);
		return new ResponseEntity<>(ResponseStructure.buildResponse(entitySaved, "Service Created  successfully.",
				HttpStatus.CREATED, HttpStatus.CREATED.value()), HttpStatus.CREATED);
	}

	@GetMapping("/getServices/{categoryID}")
	public ResponseEntity<ResponseStructure<List<ServiceDto>>> getServiceById(
			@PathVariable(value = "categoryID", required = true) String categoryId) {

		try {
			new ObjectId(categoryId);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(new ArrayList<>(), "In Valid Category Id  ID must be HexaString",
							HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}
		List<ServiceDto> servicesList = service.getServicesByCategoryId(categoryId);

		if (servicesList == null || servicesList.isEmpty()) {
			servicesList = new ArrayList<>();
			return new ResponseEntity<>(ResponseStructure.buildResponse(servicesList,
					"No Details Found With Category Id : " + categoryId + " .", HttpStatus.NOT_FOUND,
					HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
		}

		return new ResponseEntity<>(ResponseStructure.buildResponse(servicesList, "Services found successfully",
				HttpStatus.FOUND, HttpStatus.FOUND.value()), HttpStatus.OK);
	}

	@GetMapping("/getService/{serviceID}")
	public ResponseEntity<ResponseStructure<ServiceDto>> getServiceByServiceId(
			@PathVariable(value = "serviceID", required = true) String serviceId) {
		try {
			new ObjectId(serviceId);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(null, "In Valid Service Id  ID must be HexaString",
							HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}

		ServiceDto servicedomain = service.getServiceById(serviceId);
		if (servicedomain != null) {
			return new ResponseEntity<>(ResponseStructure.buildResponse(servicedomain, "Services found successfully",
					HttpStatus.FOUND, HttpStatus.FOUND.value()), HttpStatus.OK);
		}
		return new ResponseEntity<>(
				ResponseStructure.buildResponse(null, "No Details Found With Service Id : " + serviceId + " .",
						HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
				HttpStatus.OK);
	}

	@DeleteMapping("/deleteByServiceID/{serviceID}")
	public ResponseEntity<ResponseStructure<ServiceDto>> deleteService(
			@PathVariable(value = "serviceID", required = true) String serviceId) {
		try {
			new ObjectId(serviceId);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(null, "In Valid Service Id  ID must be HexaString",
							HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}

		ServiceDto servicedomain = service.getServiceById(serviceId);
		if (servicedomain != null) {
			service.deleteServiceById(serviceId);
			return new ResponseEntity<>(ResponseStructure.buildResponse(servicedomain, "Service Deleted successfully",
					HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);

		}
		return new ResponseEntity<>(ResponseStructure.buildResponse(null, "Invalid Service ID : " + serviceId + " .",
				HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
	}

	@PutMapping("/updateService/{serviceID}")
	public ResponseEntity<ResponseStructure<ServiceDto>> updateByServiceId(
			@PathVariable(value = "serviceID", required = true) String serviceId,
			@RequestBody ServiceDto domainServices) {

		try {
			new ObjectId(serviceId);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(null, "In Valid Service Id  ID must be HexaString",
							HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}
		ServiceDto servicedomain = service.getServiceById(serviceId);

		if (servicedomain == null) {
			return new ResponseEntity<>(ResponseStructure.buildResponse(null,
					"Invalid Service ID : " + serviceId + " .", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
					HttpStatus.OK);
		} else {
			ServiceDto domain = service.updateService(serviceId, domainServices);
			return new ResponseEntity<>(ResponseStructure.buildResponse(domain, "Service Updated Sucessfully",
					HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
		}
	}
}
