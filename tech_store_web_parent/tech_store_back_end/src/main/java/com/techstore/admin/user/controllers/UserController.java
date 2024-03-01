package com.techstore.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techstore.admin.user.services.UserService;
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
		return "user";
	}
}
