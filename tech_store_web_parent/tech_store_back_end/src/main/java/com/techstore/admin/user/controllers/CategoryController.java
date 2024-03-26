package com.techstore.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techstore.admin.user.services.CategoryService;
import com.techstore.common.entities.Category;
import com.techstore.common.utils.Constant;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String findAll(Model model) {

		List<Category> categories = categoryService.findAll();

		model.addAttribute(Constant.CATEGORY_LIST, categories);
		return "categories/category";
	}

	@GetMapping("/new")
	public String newCategory(Model model) {

		List<Category> categories = categoryService.listCategoriesUsedInForm();

		model.addAttribute(Constant.CATEGORY, new Category());
		model.addAttribute(Constant.CATEGORY_LIST, categories);
		model.addAttribute(Constant.PAGE_TITLE, Constant.CREATE_NEW_CATEGORY);

		return "categories/category_form";
	}
}
