package com.farmstory.repository.custom;

import com.farmstory.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    public List<Product> selectProducts();
    public Product selectProduct(int prodNo);

}