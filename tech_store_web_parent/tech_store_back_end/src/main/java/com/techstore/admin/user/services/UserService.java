package com.techstore.admin.user.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techstore.admin.user.exceptions.UserNotFoundException;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;

public interface UserService {
	List<User> findAll();

	List<Role> roleList();

	User saveUser(User user);

	boolean checkEmailExist(Integer id, String email);

	User findByid(Integer id) throws UserNotFoundException;

	void deleteUser(Integer id) throws UserNotFoundException;

	void updateUserEnableStatus(Integer id, boolean enabled);
	
	Page<User> findAllPage(int pageNumber);
}
