package com.techstore.admin.user.services;

import java.util.List;

import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;

public interface UserService {
	List<User> findAll();

	List<Role> roleList();

	User saveUser(User user);
}
