package com.techstore.admin.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.techstore.common.entities.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> listRootCategories();
}
