package com.techstore.admin.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techstore.admin.user.repositories.UserRepository;
import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

}
