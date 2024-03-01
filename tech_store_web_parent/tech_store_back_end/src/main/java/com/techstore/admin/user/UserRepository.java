package com.techstore.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techstore.common.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
}
