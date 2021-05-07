package com.skcc.category.controller;

import java.util.List;

import com.skcc.category.domain.CategoryEntity;
import com.skcc.category.service.CategoryJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class CategoryController {
	private Logger logger = LoggerFactory.getLogger(CategoryController.class);
	private CategoryJpaService categoryJpaService;
	
    @Autowired
    public CategoryController(CategoryJpaService categoryJpaService) {
    	this.categoryJpaService = categoryJpaService;
	}

	@GetMapping(value="/categories")
	@Cacheable(value="categoryCache", key="activeCategory")
	public List<CategoryEntity> getCategories() {
    	logger.info("/v1/categories called.");
    	return this.categoryJpaService.categoryEntityRepository.findByActiveOrderByPriorityAsc("active");
	}
}
