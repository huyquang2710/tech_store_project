package com.techstore.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techstore.admin.user.exceptions.UserNotFoundException;
import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;
import com.techstore.common.utils.Constant;
import com.techstore.common.utils.MessageConstant;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String listAllUser(Model model) {
		List<User> userList = userService.findAll();

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
	public String saveUser(User user, RedirectAttributes redirectAttributes) {

		System.out.println(">>>>>>>>>>>>>>> : User save: " + user);

		redirectAttributes.addFlashAttribute(Constant.MESSAGE, MessageConstant.MESSAGE_SAVE_USER_SUCCESS);
		userService.saveUser(user);
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
}
