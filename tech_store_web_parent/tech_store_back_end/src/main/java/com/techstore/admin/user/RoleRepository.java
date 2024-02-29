package com.techstore.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techstore.common.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

}
