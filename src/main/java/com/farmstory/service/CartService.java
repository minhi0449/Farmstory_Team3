package com.farmstory.service;

import com.farmstory.dto.CartRequestDTO;
import com.farmstory.dto.CartResponseDTO;
import com.farmstory.entity.Cart;
import com.farmstory.entity.Product;
import com.farmstory.repository.CartRepository;
import com.farmstory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;


    public CartRequestDTO insertCart(CartRequestDTO cartRequestDTO) {
        System.out.println("cartRequestDTO = " + cartRequestDTO);
            Optional<Cart> existcart = cartRepository.findByProductProdNo(cartRequestDTO.getProduct_id());
            Cart cart = null;
            if(existcart.isPresent()) {
                System.out.println("있음");
                cart = existcart.get();
                cart.increaseCount(cartRequestDTO.getCount());

            }else {
                System.out.println("없음");
                Product product = productRepository.findById(cartRequestDTO.getProduct_id());
                if(product != null) {
                    cart = cartRequestDTO.toEntity();
                    cart.addProduct(product);
                    cartRepository.save(cart);
                }
            }
        return modelMapper.map(cart, CartRequestDTO.class);
    }


    public List<CartResponseDTO> selectAllCartByUid(String uid) {
        List<Cart> carts = cartRepository.findByUid(uid);
        log.info("optcart" + carts);

        List<CartResponseDTO> cartDtos = carts.stream()
                .map(cart -> modelMapper.map(cart, CartResponseDTO.class)) // DTO 변환
                .collect(Collectors.toList());
        return cartDtos;
    }
}
