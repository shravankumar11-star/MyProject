package com.dermacare.category_services.util;

import java.util.Base64;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import com.dermacare.category_services.dto.CategoryDto;
import com.dermacare.category_services.dto.ServiceDto;
import com.dermacare.category_services.entity.Category;
import com.dermacare.category_services.entity.Services;

public class HelperForConversion {
	
	
	public static Services toEntity(ServiceDto dto) {
		Services entity = new Services();
		BeanUtils.copyProperties(dto, entity);
		entity.setServiceImage(base64ToByteArray(dto.getServiceImage()));
		entity.setViewImage(base64ToByteArray(dto.getViewImage()));
		entity.setCategoryId(new ObjectId());
		return entity;
	}
	
	public static ServiceDto toDto(Services entity) {
		ServiceDto dto = new ServiceDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setServiceImage(byteArrayToBase64(entity.getServiceImage()));
		dto.setViewImage(byteArrayToBase64(entity.getViewImage()));
		dto.setCategoryId(entity.getCategoryId().toString());
		dto.setServiceId(entity.getServiceId().toString());
		return dto;
	}
	
	
	public static byte[] base64ToByteArray(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

	public static String byteArrayToBase64(byte[] byteArray) {
		return Base64.getEncoder().encodeToString(byteArray);
	}
	
	
	public static Category toEntity(CategoryDto dto) {
		Category category = new Category();
		BeanUtils.copyProperties(dto, category);
		category.setCategoryImage(base64ToByteArray(dto.getCategoryImage()));
		return category;
	}

	public static CategoryDto toDto(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setCategoryImage(byteArrayToBase64(category.getCategoryImage()));
		dto.setCategoryId(category.getCategoryId().toString());
		dto.setCategoryName(category.getCategoryName());
		return dto;
	}
	
	public static List<CategoryDto> converToDtos(List<Category> listOfCategories) {
		return listOfCategories.stream().map(HelperForConversion::toDto).toList();
	}

	public static List<ServiceDto> toDtos(List<Services> servicesList) {
		return servicesList.stream().map(HelperForConversion::toDto).toList();
	}

}
