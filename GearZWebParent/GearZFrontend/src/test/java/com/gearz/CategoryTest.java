package com.gearz;

import java.util.List;
import java.util.Set;

import com.gearz.common.entity.Category;
import com.gearz.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryTest {
    @Autowired
    private CategoryRepository repo;

    @Test
    public void testListEnabledCategories() {
        List<Category> c = repo.findAllEnabledMainCategories();
        c.forEach(cat -> {
            Set<Category> children = cat.getChildren();
            if (children != null) {
                for (Category category : children) {
                    System.out.println(category.getName());
                }
            }
        });
    }

}
