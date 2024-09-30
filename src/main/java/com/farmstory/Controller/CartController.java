package com.farmstory.Controller;


import com.farmstory.dto.CartRequestDTO;
import com.farmstory.dto.CartResponseDTO;
import com.farmstory.service.CartService;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 지현님&상훈님 - market
@Log4j2
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    // cart
    @GetMapping("/market/cart")
    public String cart(@RequestParam String uid, Model model) {
        log.info("uid :" + uid);
        List<CartResponseDTO> cartdto = cartService.selectAllCartByUid(uid);
        log.info("cartdto : " + cartdto);
        model.addAttribute("cart", cartdto);

        return "/market/cart";
    }

    @PostMapping("/market/cart")
    public ResponseEntity<CartRequestDTO> cart(@RequestBody CartRequestDTO cartRequestDTO, Model model) {

        CartRequestDTO dto = cartService.insertCart(cartRequestDTO);

        // 나중에 빼기
        dto.setUid("ghkdtnqls95");
        return ResponseEntity
                .ok()
                .body(dto);
    }
    @GetMapping("/market/order")
    public String order() {
        // TODO: 사용자의 정보 가져와야함(name, hp, point)

        return "/market/order";
    }

}
