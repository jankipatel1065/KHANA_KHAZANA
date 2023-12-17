package com.humber.khana_khazana.repositories;

import com.humber.khana_khazana.models.Category;
import com.humber.khana_khazana.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {
    Optional<Category> findCategoryById(Long categoryId);
}
