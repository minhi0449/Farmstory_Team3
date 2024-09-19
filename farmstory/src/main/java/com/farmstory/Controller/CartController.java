package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 지현님&상훈님 - market
@Controller
public class CartController {

    // cart
    @GetMapping("/market/cart")
    public String cart() {
        return "/market/cart";
    }
    @GetMapping("/market/order")
    public String order() {
        // TODO: 사용자의 정보 가져와야함(name, hp, point)

        return "/market/order";
    }

}
