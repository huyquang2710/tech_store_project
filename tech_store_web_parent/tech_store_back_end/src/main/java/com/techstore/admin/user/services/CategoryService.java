package com.techstore.admin.user.services;

import java.util.List;

import com.techstore.common.entities.Category;

public interface CategoryService {
	List<Category> findAll();

	List<Category> listCategoriesUsedInForm();
}
