package com.techstore.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.techstore.admin.user.repositories.CategoryRepository;
import com.techstore.common.entities.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void testCreateRootCategory() {
		Category category = new Category("SAMSUNG");

		Category saveCategory = categoryRepository.save(category);

		assertThat(saveCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(1);
		Category subCategory = new Category("Desktop", parent);

		Category saveCategory = categoryRepository.save(subCategory);

		assertThat(saveCategory.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateMultiSubCategory() {
		Category parent = new Category(2);

		Category subCate1 = new Category("Camera", parent);
		Category subCate2 = new Category("Smartphone", parent);

		categoryRepository.saveAll(List.of(subCate1, subCate2));
	}

	@Test
	public void testGetCategory() {
		Category cate = categoryRepository.findById(1).get();

		System.out.println(cate.getName());

		Set<Category> children = cate.getChildren();

		for (Category subCate : children) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>> " + subCate.getName());
		}

		assertThat(children.size()).isGreaterThan(0);

	}

	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = categoryRepository.findAll();

		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());

				Set<Category> children = category.getChildren();

				for (Category subCate : children) {
					System.out.println("--" + subCate.getName());
					printChildren(subCate, 1);
				}
			}
		}
	}

	@Test
	public void testGetListRootCategories() {
		List<Category> rootCategories = categoryRepository.listRootCategories();

		rootCategories.forEach(cat -> System.out.println(cat.getName()));
	}

	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();

		for (Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			System.out.println(subCategory.getName());

			printChildren(subCategory, newSubLevel);
		}
	}

}
