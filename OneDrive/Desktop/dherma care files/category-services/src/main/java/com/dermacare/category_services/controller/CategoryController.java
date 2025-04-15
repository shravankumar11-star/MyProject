package com.dermacare.category_services.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

import com.dermacare.category_services.dto.CategoryDto;
import com.dermacare.category_services.entity.Category;
import com.dermacare.category_services.repository.CategoryRepository;
import com.dermacare.category_services.service.CategoryService;
import com.dermacare.category_services.service.Impl.CategoryServiceImpl;
import com.dermacare.category_services.util.ResponseStructure;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

	private final CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@PostMapping("/addCategory")
	public ResponseEntity<ResponseStructure<CategoryDto>> addNewCategory(@RequestBody CategoryDto dto) {
		if (categoryService.findByCategoryName(dto.getCategoryName())) {
			return new ResponseEntity<>(ResponseStructure.buildResponse(null,
					"Category Already Exists With Same Name " + dto.getCategoryName(), HttpStatus.NOT_ACCEPTABLE,
					HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
		}
		CategoryDto savedEntiy = categoryService.addCategory(dto);
		return new ResponseEntity<>(ResponseStructure.buildResponse(savedEntiy, "Category Saved Sucessfully",
				HttpStatus.CREATED, HttpStatus.CREATED.value()), HttpStatus.OK);
	}

	@GetMapping("/getCategories")
	public ResponseEntity<ResponseStructure<List<CategoryDto>>> getAllCategory() {
		List<CategoryDto> listOfCategories = categoryService.findAllCategories();

		if (listOfCategories == null || listOfCategories.isEmpty()) {
			listOfCategories = new ArrayList<>();
			return new ResponseEntity<>(ResponseStructure.buildResponse(listOfCategories,
					"Categories Not Found Please Add Categories", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(ResponseStructure.buildResponse(listOfCategories, "Categories Found",
				HttpStatus.FOUND, HttpStatus.FOUND.value()), HttpStatus.OK);
	}

	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ResponseStructure<CategoryDto>> deleteCategoryById(
			@PathVariable(value = "categoryId") String categoryId) {
		try {
			new ObjectId(categoryId);
		} catch (Exception e) {
			return new ResponseEntity<>(
					ResponseStructure.buildResponse(null, "In Valid Category Id  ID must be HexaString",
							HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}
		CategoryDto CategoryDto = categoryService.getCategorById(categoryId);
		if (CategoryDto == null) {
			return new ResponseEntity<>(ResponseStructure.buildResponse(null, "Please Provide Valid Category Id",
					HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
		}
		categoryService.deleteById(categoryId);
		return new ResponseEntity<>(ResponseStructure.buildResponse(CategoryDto, "Category Deleted Sucessfully",
				HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
	}

	@PutMapping("updateCategory/{categoryId}")
	public ResponseEntity<ResponseStructure<CategoryDto>> updateCategory(@PathVariable ObjectId categoryId,
			@RequestBody CategoryDto updatedCategory) {
		try {
			new ObjectId();
		} catch (RuntimeException e) {
			new ResponseEntity<>(ResponseStructure.buildResponse(null, "Invalid Category Please Provide Valid Id",
					HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
		}
		
		CategoryDto savedCategory = categoryService.updateCategoryById(categoryId, updatedCategory);
		return new ResponseEntity<>(ResponseStructure.buildResponse(savedCategory, "Category Updated Sucess Fully",
				HttpStatus.OK, HttpStatus.OK.value()), HttpStatus.OK);
		
	}
}
