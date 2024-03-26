package com.techstore.admin.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techstore.admin.user.repositories.CategoryRepository;
import com.techstore.admin.user.services.CategoryService;
import com.techstore.common.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {

		List<Category> categoties = (List<Category>) categoryRepository.findAll();

		return categoties;
	}

	@Override
	public List<Category> listCategoriesUsedInForm() {

		List<Category> categoriesInForm = new ArrayList<>();
		// Iterable<Category> categoriesInDB = categoryRepository.findAll();
		List<Category> categoriesInDB = (List<Category>) categoryRepository.findAll();

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesInForm.add(new Category(category.getName()));

				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesInForm.add(new Category(name));

					listChildren(categoriesInForm, subCategory, 1);

				}
			}
		}

		return categoriesInForm;
	}

	private void listChildren(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();

			categoriesUsedInForm.add(new Category(name));

			listChildren(categoriesUsedInForm, subCategory, newSubLevel);
		}
	}

}
