package com.humber.khana_khazana.service;

import com.humber.khana_khazana.models.Category;
import com.humber.khana_khazana.models.Product;
import com.humber.khana_khazana.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CategoryService {
    CategoryRepo categoryRepo;
    @Autowired
    public  CategoryService(CategoryRepo categoryRepo)
    {
        this.categoryRepo= categoryRepo;
    }

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepo.findAll();
    }
    public void addCategories(Category p)
    {
        this.categoryRepo.save(p);
    }
    public Optional<Category> findCategoryByCategoryId(Long categoryID) {
        return this.categoryRepo.findCategoryById(categoryID);
    }
    public void deleteCategoryById(Long id) {
        categoryRepo.deleteById(id);
    }
}
