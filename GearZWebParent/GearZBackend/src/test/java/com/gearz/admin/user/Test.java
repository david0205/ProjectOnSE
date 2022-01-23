package com.gearz.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.gearz.admin.repository.ProductRepository;
import com.gearz.admin.repository.RoleRepository;
import com.gearz.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class Test {

	@Autowired
	private RoleRepository repo;

	@Autowired
	private ProductRepository pRepo;

	@org.junit.jupiter.api.Test
	public void testCreateFirstRoleAdmin() {
		Role admin = Role.builder().role_name("Admin").build();
		Role savedRole = repo.save(admin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	@org.junit.jupiter.api.Test
	public void testCreateRoles() {
		Role salesperson = Role.builder().role_name("Salesperson").build();
		Role assistant = Role.builder().role_name("Assistant").build();
		Role editor = Role.builder().role_name("Editor").build();
		Role shipper = Role.builder().role_name("Shipper").build();

		repo.saveAll(List.of(salesperson, assistant, editor, shipper));
	}

	@org.junit.jupiter.api.Test
	public void testGetImagePath() {
		String path = pRepo.findById(3).get().getMainImagePath();
		System.out.println(path);
		// assertThat(path).isEmpty();
	}
}
