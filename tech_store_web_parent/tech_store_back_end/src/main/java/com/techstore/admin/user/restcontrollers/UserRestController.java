package com.techstore.admin.user.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techstore.admin.user.services.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@PostMapping("/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return userService.checkEmailExist(id, email) ? "OK" : "Duplicated";
	}

}
