package com.techstore.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String listAllUser(Model model) {
		List<User> userList = userService.findAll();

		model.addAttribute("user", userList);
		model.addAttribute("pageTitle", "User List");
		
		return "users/user";
	}

	@GetMapping("/new")
	public String newUser(Model model) {

		// get role list
		List<Role> roleList = userService.roleList();

		User user = new User();
		user.setEnabled(true);

		model.addAttribute("user", user);
		model.addAttribute("rolesList", roleList);
		model.addAttribute("pageTitle", "New User");

		return "users/user_form";
	}

	@PostMapping("/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {

		System.out.println(">>>>>>>>>>>>>>> : User save: " + user);

		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		userService.saveUser(user);
		return "redirect:/users";
	}
}
