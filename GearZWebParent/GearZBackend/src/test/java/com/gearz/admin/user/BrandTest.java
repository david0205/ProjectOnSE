package com.gearz.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.gearz.admin.repository.BrandRepository;
import com.gearz.admin.repository.CategoryRepository;
import com.gearz.common.entity.Brand;
import com.gearz.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandTest {
	@Autowired
	private BrandRepository repo;

	@Autowired
	private CategoryRepository cRepo;

	@Test
	public void testCreateNewBrand() {
		Category processor = cRepo.findById(5).get();
		Brand intel = new Brand("Intel", "dummy.png");
		intel.getCategories().add(processor);

		Brand saved = repo.save(intel);

		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindAll() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(System.out::println);

		assertThat(brands).isNotEmpty();
	}
}
