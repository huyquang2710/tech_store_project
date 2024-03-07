package com.techstore.admin.user.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techstore.admin.common.utils.FileUploadUtils;
import com.techstore.admin.user.exceptions.UserNotFoundException;
import com.techstore.admin.user.services.UserService;
import com.techstore.admin.user.services.impl.UserServiceImpl;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;
import com.techstore.common.utils.Constant;
import com.techstore.common.utils.MessageConstant;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping
//	public String listAllUser(Model model) {
//		List<User> userList = userService.findAll();
//
//		model.addAttribute(Constant.USER, userList);
//		model.addAttribute(Constant.PAGE_TITLE, Constant.USER_LIST);
//
//		return "users/user";
//	}

	@GetMapping
	public String listFirstPage(Model model) {
		return listAllByPage(1, model);
	}

	@GetMapping("/page/{pageNum}")
	public String listAllByPage(@PathVariable("pageNum") int pageNum, Model model) {
		Page<User> pageUser = userService.findAllPage(pageNum);
		List<User> userList = pageUser.getContent();

		long startCount = (pageNum - 1) * UserServiceImpl.USERS_PER_PAGE + 1;
		long endCount = startCount + UserServiceImpl.USERS_PER_PAGE - 1;
		if (endCount > pageUser.getTotalElements()) {
			endCount = pageUser.getTotalElements();
		}

		System.out.println("Page Num: " + pageNum);
		System.out.println("Total element: " + pageUser.getTotalElements());
		System.out.println("Total page: " + pageUser.getTotalPages());
		System.out.println("statrt count: " + startCount);
		System.out.println("end count: " + endCount);

		model.addAttribute(Constant.CURRENT_PAGE, pageNum);
		model.addAttribute(Constant.START_COUNT, startCount);
		model.addAttribute(Constant.END_COUNT, endCount);
		model.addAttribute(Constant.TOTAL_ITEMS, pageUser.getTotalElements());
		model.addAttribute(Constant.TOTAL_PAGES, pageUser.getTotalPages());
		model.addAttribute(Constant.USER, userList);

		model.addAttribute(Constant.PAGE_TITLE, Constant.USER_LIST);
		return "users/user";
	}

	@GetMapping("/new")
	public String newUser(Model model) {

		// get role list
		List<Role> roleList = userService.roleList();

		User user = new User();
		user.setEnabled(true);

		model.addAttribute(Constant.USER, user);
		model.addAttribute(Constant.ROLE_LIST, roleList);
		model.addAttribute(Constant.PAGE_TITLE, Constant.CREATE_NEW_USER);

		return "users/user_form";
	}

	@PostMapping("/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.saveUser(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtils.cleanDir(uploadDir);
			FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

		} else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			userService.saveUser(user);
		}

		System.out.println(">>>>>>>>>>>>>>> : User save: " + user);

		redirectAttributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_SAVE_USER_SUCCESS);
		return "redirect:/users";
	}

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			User user = userService.findByid(id);

			// get role list
			List<Role> roleList = userService.roleList();

			model.addAttribute(Constant.USER, user);
			model.addAttribute(Constant.PAGE_TITLE, Constant.UPDATE_USER + ": " + user.getLastName());
			model.addAttribute(Constant.ROLE_LIST, roleList);

			return "users/user_form";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(Constant.MESSAGE, e.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_DELETE_USER_SUCCESS);
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute(Constant.MESSAGE, e.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable("status") boolean enabled,
			Model model, RedirectAttributes attributes) {

		userService.updateUserEnableStatus(id, enabled);

		attributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_UPDATED_ENABLE_STATUS);

		return "redirect:/users";
	}
}
