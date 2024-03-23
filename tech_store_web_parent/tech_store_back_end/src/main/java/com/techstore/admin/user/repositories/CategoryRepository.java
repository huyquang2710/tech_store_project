package com.techstore.admin.user.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.techstore.common.entities.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
