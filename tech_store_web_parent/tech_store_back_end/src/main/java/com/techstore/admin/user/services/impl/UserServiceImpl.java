package com.techstore.admin.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techstore.admin.user.repositories.RoleRepository;
import com.techstore.admin.user.repositories.UserRepository;
import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public List<Role> roleList() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public User saveUser(User user) {

		encodePass(user);

		return userRepository.save(user);
	}

	private void encodePass(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
	}

	@Override
	public boolean checkEmailExist(String email) {

		User user = userRepository.findUserByEmail(email);

		return user == null;
	}

}
