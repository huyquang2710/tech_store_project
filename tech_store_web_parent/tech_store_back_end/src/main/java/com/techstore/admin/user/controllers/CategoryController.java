package com.techstore.admin.user.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techstore.admin.common.utils.FileUploadUtils;
import com.techstore.admin.user.services.CategoryService;
import com.techstore.common.entities.Category;
import com.techstore.common.utils.Constant;
import com.techstore.common.utils.MessageConstant;

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

	@PostMapping("/save")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		category.setImage(fileName);

		Category saveCategory = categoryService.saveCategory(category);

		String uploadDir = "../category-images/" + saveCategory.getId();
		FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

		redirectAttributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_SAVE_CATEGORY_SUCCESS);

		return "redirect:/categories";
	}
}
