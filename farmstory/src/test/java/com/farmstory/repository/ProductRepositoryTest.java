package com.farmstory.repository;

import com.farmstory.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void insertProductTest() {
        Product product = Product.builder()
                .prodName("새우깡")
                .type("과자")
                .price(1200)
                .discount(5)
                .deliveryfee(2500)
                .stock(10000)
                .img1("")
                .img2("")
                .img3("")
                .etc("")
//                .uid("A101")
                .build();
        productRepository.save(product);
    }

    @Test
    public void selectProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("---------------------------products----------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
