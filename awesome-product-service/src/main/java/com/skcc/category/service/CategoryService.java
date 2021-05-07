package com.skcc.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skcc.category.domain.Category;
import com.skcc.category.repository.CategoryMapper;

@Service
public class CategoryService {
	
	private CategoryMapper categoryMapper;
	@Autowired
	public CategoryService(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	public List<Category> findAll(String txId) {
		return this.categoryMapper.findAll();
	}
}
