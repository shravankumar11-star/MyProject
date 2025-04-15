package com.dermacare.category_services.service.Impl;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dermacare.category_services.dto.CategoryDto;
import com.dermacare.category_services.entity.Category;
import com.dermacare.category_services.entity.Services;
import com.dermacare.category_services.repository.CategoryRepository;
import com.dermacare.category_services.repository.ServicesRepository;
import com.dermacare.category_services.service.CategoryService;
import com.dermacare.category_services.util.HelperForConversion;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ServicesRepository serviceManagmentRepository;

	@Autowired
	ServicesServiceImpl service;

	public CategoryDto addCategory(CategoryDto dto) {
		Category category = HelperForConversion.toEntity(dto);
		Category savedCategory = repository.save(category);
		return HelperForConversion.toDto(savedCategory);
	}

	public boolean findByCategoryName(String categoryName) {
		Optional<Category> category = repository.findByCategoryName(categoryName);
		return category.isPresent();
	}

	public List<CategoryDto> findAllCategories() {
		List<Category> listOfCategories = repository.findAll();
		if (listOfCategories.isEmpty()) {
			return null;
		}
		return HelperForConversion.converToDtos(listOfCategories);
	}

	public CategoryDto getCategorById(String categoryId) {
		Optional<Category> categoryOptional = repository.findById(new ObjectId(categoryId));
		if (categoryOptional.isEmpty()) {
			return null;
		}
		Category category = categoryOptional.get();
		return HelperForConversion.toDto(category);
	}

	public void deleteById(String categoryId) {
		repository.deleteById(new ObjectId(categoryId));
		service.deleteServiceByCategoryId(new ObjectId(categoryId));
	}

	public CategoryDto updateCategoryById(ObjectId categoryId, CategoryDto updateDto) {

		Optional<Category> existingCategoryOptional = repository.findById(categoryId);

		if (!existingCategoryOptional.isPresent()) {
			throw new NoSuchElementException("Category not found: " + categoryId);
		}

		boolean present = findByCategoryName(updateDto.getCategoryName());

		if (present) {
			throw new RuntimeException("Duplicate Category Name already Present: " + updateDto.getCategoryName());
		}

		Category existingCategory = existingCategoryOptional.get();
		String categoryName = existingCategory.getCategoryName();
		existingCategory.setCategoryName(updateDto.getCategoryName());

		if (updateDto.getCategoryImage() != null) {
			byte[] categoryImageBytes = Base64.getDecoder().decode(updateDto.getCategoryImage());
			existingCategory.setCategoryImage(categoryImageBytes);
		}

		Category savedCategory = repository.save(existingCategory);

		List<Services> services = serviceManagmentRepository.findByCategoryName(categoryName);
		for (Services service : services) {
			service.setCategoryName(updateDto.getCategoryName());
			serviceManagmentRepository.save(service);
		}
		return HelperForConversion.toDto(savedCategory);
	}

	@Override
	public boolean findByCategoryId(String categoryId) {
		Optional<Category> optional = repository.findById(new ObjectId());
		return optional.isPresent();
	}

}
