package com.AdminService.feign;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.AdminService.dto.CategoryDto;
import com.AdminService.util.ResponseStructure;

@FeignClient(name = "CATEGORY-SERVICES")
public interface CategoryFeign {

    @PostMapping("/api/v1/category/addCategory")
    ResponseEntity<ResponseStructure<CategoryDto>> addNewCategory(@RequestBody CategoryDto dto);

    @GetMapping("/api/v1/category/getCategories")
    ResponseEntity<ResponseStructure<List<CategoryDto>>> getAllCategory();

    @DeleteMapping("/api/v1/category/deleteCategory/{categoryId}")
    ResponseEntity<ResponseStructure<CategoryDto>> deleteCategoryById(
            @PathVariable("categoryId")  String categoryId);  // Use string for compatibility

    @PutMapping("/api/v1/category/updateCategory/{categoryId}")
    ResponseEntity<ResponseStructure<CategoryDto>> updateCategory(
            @PathVariable("categoryId") ObjectId categoryId,
            @RequestBody CategoryDto updatedCategory);
}
