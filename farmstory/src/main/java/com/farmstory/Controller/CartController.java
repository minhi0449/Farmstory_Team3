package com.farmstory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 지현님&상훈님 - market
@Controller
public class CartController {

    @GetMapping("/market/view")
    public String view() {
        return "/market/view";
    }
    @GetMapping("/market/list")
    public String list() {
        return "/market/list";
    }
    // cart
    @GetMapping("/market/cart")
    public String cart() {
        return "/market/cart";
    }
    @GetMapping("/market/order")
    public String order() {
        return "/market/order";
    }

}
