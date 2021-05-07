package com.skcc.category.service;

import com.skcc.category.controller.CategoryController;
import com.skcc.category.domain.CategoryEntity;
import com.skcc.category.repository.CategoryEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryJpaService {
    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    public CategoryEntityRepository categoryEntityRepository;

    @Autowired
    public CategoryJpaService(CategoryEntityRepository categoryEntityRepository) {
        logger.info("CategoryJpaService Called");
        this.categoryEntityRepository = categoryEntityRepository;
    }
}
