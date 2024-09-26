package com.farmstory.Controller;

import com.farmstory.dto.CartDTO;
import com.farmstory.service.CartService;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// 지현님&상훈님 - market
@Log4j2
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    // cart
    @PostMapping("/market/cart")
    public String cart(@RequestBody CartDTO cartDTO) {


        cartService.insertCart(cartDTO);
        log.info(cartDTO);
        return "/market/cart";
    }
    @GetMapping("/market/order")
    public String order() {
        // TODO: 사용자의 정보 가져와야함(name, hp, point)

        return "/market/order";
    }

}
