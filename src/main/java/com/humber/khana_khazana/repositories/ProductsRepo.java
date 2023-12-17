package com.humber.khana_khazana.repositories;

import com.humber.khana_khazana.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends CrudRepository<Product, Long> {
    Product findProductById(Long productId);
    public List<Product> deleteProductsByCategoryId(Long categoryId);
}
