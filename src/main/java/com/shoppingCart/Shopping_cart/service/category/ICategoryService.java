package com.shoppingCart.Shopping_cart.service.category;

import com.shoppingCart.Shopping_cart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategorys();

    Category addCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategoryById(Long id);
}
