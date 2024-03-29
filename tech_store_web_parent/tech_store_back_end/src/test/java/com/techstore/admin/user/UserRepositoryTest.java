package com.techstore.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.techstore.admin.user.repositories.UserRepository;
import com.techstore.common.entities.Role;
import com.techstore.common.entities.User;
import com.techstore.common.utils.Constant;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManagerl;

	@Test
	public void testCreateUserWithOneRole() {
		Role admineRole = testEntityManagerl.find(Role.class, Constant.INT_1);
		User userAdmin = new User("admin@gmail.com", "1234", "Quang", "Huy");
		userAdmin.addRole(admineRole);

		User saveUser = userRepository.save(userAdmin);

		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateUserWithManyRole() {
		User userOliver = new User("oliver@gmail.com", "1234", "Oliver", "Jake");

		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);

		userOliver.addRole(roleEditor);
		userOliver.addRole(roleAssistant);

		User saveUser = userRepository.save(userOliver);

		assertThat(saveUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindAllUser() {
		Iterable<User> userList = userRepository.findAll();
		userList.forEach(user -> System.out.println(">>>>>>>>>>>>>>>> User Info : " + user));
	}

	@Test
	public void testFindUserById() {
		User findUser = userRepository.findById(1).get();
		System.out.println(">>>>>>>>>>>>>>>> User Info : " + findUser);
		assertThat(findUser).isNotNull();
	}

	@Test
	public void testUpdateUser() {
		User findById = userRepository.findById(1).get();

		System.out.println(">>>>>>>>>>>>>>>> Before User Info : " + findById);

		findById.setEnabled(true);
		findById.setFirstName("Phung");

		userRepository.save(findById);

		System.out.println(">>>>>>>>>>>>>>>> Alter User Info : " + findById);

	}

	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		userRepository.deleteById(userId);
	}

	@Test
	public void testFindUserByEmail() {
		String email1 = "user@gmail.com";
		String email2 = "admin@gmail.com";

		User user = userRepository.findUserByEmail(email2);

		System.out.println(">>>>>>>>>>>>>>>> User Info : " + user);

		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 1;
		Long coungById = userRepository.countById(id);

		assertThat(coungById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testUpdateStatusEnabled() {
		Integer id = 1;
		userRepository.updateEnableStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findAll(pageable);

		List<User> userList = page.getContent();

		userList.forEach(user -> System.out.println("USER INFO: " + user));

		assertThat(userList.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "Remzi";

		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepository.findByKey(keyword, pageable);

		List<User> listUsers = page.getContent();

		listUsers.forEach(user -> System.out.println(user));	

		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
