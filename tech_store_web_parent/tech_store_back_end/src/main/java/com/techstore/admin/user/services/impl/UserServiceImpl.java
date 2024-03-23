package com.techstore.admin.user.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techstore.admin.user.exceptions.UserNotFoundException;
import com.techstore.admin.user.repositories.RoleRepository;
import com.techstore.admin.user.repositories.UserRepository;
import com.techstore.admin.user.services.UserService;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;
import com.techstore.common.utils.Constant;
import com.techstore.common.utils.MessageConstant;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	public static final int USERS_PER_PAGE = 4;

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

		boolean isUpdatingUser = (user.getId() != null);

		if (isUpdatingUser) {
			User existingUser = userRepository.findById(user.getId()).get();

			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePass(user);
			}
		} else {
			encodePass(user);
		}
		return userRepository.save(user);
	}

	@Override
	public boolean checkEmailExist(Integer id, String email) {

		User user = userRepository.findUserByEmail(email);

		if (user == null)
			return true;

		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (user != null)
				return false;
		} else {
			if (user.getId() != id) {
				return false;
			}
		}
		return true;
	}

	@Override
	public User findByid(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (Exception e) {
			throw new UserNotFoundException(MessageConstant.MESSAGE_CANNOT_FIND_USER + id);
		}
	}

	@Override
	public void deleteUser(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException(MessageConstant.MESSAGE_CANNOT_FIND_USER + id);
		}
		userRepository.deleteById(id);
	}

	@Override
	public void updateUserEnableStatus(Integer id, boolean enabled) {
		userRepository.updateEnableStatus(id, enabled);
	}

	@Override
	public Page<User> findAllPage(int pageNumber, String sortField, String sortDir, String keyword) {

		Sort sort = Sort.by(sortField);
		sort = sortDir.equals(Constant.ASC) ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE, sort);

		if (keyword != null) {
			return userRepository.findByKey(keyword, pageable);
		}
		return userRepository.findAll(pageable);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User updateAccount(User userInForm) {

		User userInDB = userRepository.findById(userInForm.getId()).get();

		if (!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePass(userInDB);
		}

		if (userInForm.getPhotos() != null) {
			userInDB.setPhotos(userInForm.getPhotos());
		}

		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());

		return userRepository.save(userInDB);
	}

	private void encodePass(User user) {
		String encodedPass = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPass);
	}

}
