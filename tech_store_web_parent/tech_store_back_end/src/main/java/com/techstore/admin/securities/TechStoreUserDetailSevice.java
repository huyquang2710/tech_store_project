package com.techstore.admin.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.techstore.admin.user.repositories.UserRepository;
import com.techstore.common.entities.User;
import com.techstore.common.utils.MessageConstant;

public class TechStoreUserDetailSevice implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(email);
		if (user != null) {
			return new TechStoreUserDetail(user);
		}
		throw new UsernameNotFoundException(MessageConstant.MESSAGE_CANNOT_FIND_USER + email);
	}

}
