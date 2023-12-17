package com.humber.khana_khazana.service;

import com.humber.khana_khazana.models.Product;
import com.humber.khana_khazana.repositories.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private ItemService itemService;
        ProductsRepo productsRepo;
        @Autowired
        public  ProductsService(ProductsRepo productsRepo)
        {
            this.productsRepo= productsRepo;
        }

        public List<Product> getAllProducts() {
            return (List<Product>) productsRepo.findAll();
        }
    public List<Product> getAllProductsByStatus() {
        List<Product> allProducts = (List<Product>) productsRepo.findAll();
        return allProducts.stream().filter(Product::isStatus).collect(Collectors.toList());
    }

    public void addProducts(Product p)
        {
             this.productsRepo.save(p);
        }
        public Optional<Product> findProductByProductId(Long productId) {
        return Optional.ofNullable(this.productsRepo.findProductById(productId));
    }
    public void deleteProductById(Long id) {
        productsRepo.deleteById(id);
    }
    public Product productByid(Long productId) {
        return this.productsRepo.findProductById(productId);
    }

    public void deleteProductByIdCategoryId(Long id) {
            productsRepo.deleteProductsByCategoryId(id);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
           return  productsRepo.deleteProductsByCategoryId(categoryId);
    }
    public void deleteProductAndItems(Long productId) {
        Product product = productsRepo.findById(productId).orElse(null);
        if (product != null) {
            // Delete items associated with the product
            itemService.deleteItemsByProduct(product);

            // Now, delete the product
            productsRepo.delete(product);
        }
    }

}
