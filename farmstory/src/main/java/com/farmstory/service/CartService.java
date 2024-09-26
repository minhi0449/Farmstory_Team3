package com.farmstory.service;

import com.farmstory.dto.CartDTO;
import com.farmstory.entity.Cart;
import com.farmstory.entity.Product;
import com.farmstory.repository.CartRepository;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;


    public void insertCart(CartDTO cartDTO) {
        Product product = productRepository.findById(cartDTO.getProduct_id());
        log.info("Insert cart " + product);
        Cart cart = cartDTO.toEntity();
        cart.addProduct(product);
//
//        Cart cart = Cart.builder()
//                .product(product)
//                .count(cartDTO.getCount())
//                .build();

        cartRepository.save(cart);

        cart.getCartNo();
    }


}
