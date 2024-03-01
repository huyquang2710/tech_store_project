package com.techstore.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.techstore.admin.user.repositories.RoleRepository;
import com.techstore.common.entities.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role saveRole = roleRepository.save(roleAdmin);

		assertThat(saveRole.getId()).isGreaterThan(0);
	}

	@Test
	public void testRestRole() {
		Role roleSalesperson = new Role("Salesperson",
				"manage product price, " + "customers, shipping, orders and sales report");
		Role roleEditor = new Role("Editor", "manage categories, brands, " + "prodcuts, articles and menu");
		Role roleShipper = new Role("Shipper", "view product, view orders, " + "and update order status");
		Role roleAssistant = new Role("Assistant", "manage question and review");

		roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
	}
}
