package com.humber.khana_khazana.controllers;

import com.humber.khana_khazana.models.*;
import com.humber.khana_khazana.repositories.CategoryRepo;
import com.humber.khana_khazana.service.*;
//import com.humber.khana_khazana.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ApplicationController {

    private final ProductsService productService;

    private final CategoryService categoryService;

    private CategoryRepo categoryRepo;
    private  OrderService orderService;
    private ItemService itemService;


    @Autowired
    public ApplicationController(ProductsService productService,
                                 CategoryService categoryService,
                                 CategoryRepo categoryRepo,
                                 OrderService orderService,
                                 ItemService itemService
                                 ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
        this.orderService=orderService;
        this.itemService=itemService;
    }
    @GetMapping("/index")
    public String GoIndex(Model model)
    {
        model.addAttribute("product", new Product());
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);

        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("orderList", orderService.getAllOrders());
        return "index";
    }

    @GetMapping("/customer")
    public String addOrders(Model model){
        model.addAttribute("order", new Order());

        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        model.addAttribute("item", new Item());

        return "Customers";
    }
    @PostMapping("/save/order")
    public String saveOrder(@ModelAttribute Order order, Model model) {
        // Assuming you have a method to save the order in your OrderService
        orderService.addOrders(order);

        return "redirect:/customer";
    }

    @PostMapping("/save/orderItem")
    public String saveOrderItem(@ModelAttribute Item item, Model model) {
        // Assuming you have a method to save the order item in your ItemService
        itemService.addItems(item);

        return "redirect:/customer";
    }




    @GetMapping("/getProducts")
    public String showCategories(Model model) {
        model.addAttribute("product", new Product());
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);

        return "Products";
    }


    @GetMapping("/add/products")
    public String addProductPage(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepo.findAll());
        return "add-products";
    }
    @PostMapping("/add/products")
    public String addProduct(Model model, @Valid @ModelAttribute Product product
            , BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("product",product);
            if(product.getId()!=0 || product.getId()==null) model.addAttribute("edit",true);
            return "add-products";
        }
        this.productService.addProducts(product);
        return "redirect:/getProducts";
    }
    @GetMapping("/getCategories")
    public String showcategoryForm(Model model) {
        model.addAttribute("category", new Category());
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "Category";
    }
    @GetMapping("/edit/product/{productId}")
    public String editCar(@PathVariable Long productId, Model model) {
        Product product = this.productService.findProductByProductId(productId).get();
        model.addAttribute("product", product );
        model.addAttribute("edit", true);
        model.addAttribute("categories", categoryRepo.findAll());
        return "add-products";
    }
    @PostMapping("/delete/product")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProductById(id);

        return "redirect:/getProducts"; // Redirect back to the pet list page.
    }
    @GetMapping("/add/categories")
    public String addCategoryPage(Model model){
        model.addAttribute("category", new Category());
        return "add-categories";
    }
    @PostMapping("/add/categories")
    public String addCategory(Model model, @Valid @ModelAttribute Category category
            , BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("category",category);
            if(category.getId()!=0 || category.getId()==null) model.addAttribute("edit",true);
            return "add-categories";
        }
        this.categoryService.addCategories(category);
        return "redirect:/getCategories";
    }
    @GetMapping("/edit/category/{categoryId}")
    public String editCategory(@PathVariable Long categoryId, Model model) {
        Category    category = this.categoryService.findCategoryByCategoryId(categoryId).get();
        model.addAttribute("category", category );
        model.addAttribute("edit", true);

        return "add-categories";
    }
    public void deleteOrderItemsByCategoryId(Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        for (Product product : products) {
            itemService.deleteItemsByProductId(product.getId());
        }
    }

    @PostMapping("/delete/category")
    public String deleteCAtegory(@RequestParam Long id) {
        categoryService.deleteCategoryById(id);

        return "redirect:/getCategories"; // Redirect back to the pet list page.
    }



}
