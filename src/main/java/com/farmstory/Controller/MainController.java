package com.farmstory.Controller;


import com.farmstory.dto.ProductDTO;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Log4j2
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ProductService productService;
    @GetMapping(value ={"/index","/"})
    public String index(Model model) {
        List<ProductDTO> products = productService.selectProducts();
        log.info(products.toString());
        model.addAttribute("products", products);
        return "/index";

    }
}
