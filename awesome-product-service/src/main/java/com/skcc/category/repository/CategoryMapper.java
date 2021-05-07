package com.skcc.category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.skcc.category.domain.Category;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper {
	
	List<Category> findAll();
}
