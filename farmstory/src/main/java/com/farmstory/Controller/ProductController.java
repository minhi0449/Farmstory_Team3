package com.farmstory.Controller;

import com.farmstory.dto.ProductDTO;

import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// 수빈님 - product
@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @GetMapping("/admin/product/register")
    public String productRegister() {
        return "/admin/product/register";
    }
    @PostMapping("/admin/product/register")
    public String productRegister(ProductDTO productDTO, MultipartFile image) {

        productService.insertProduct(productDTO,image);
        return "redirect:/admin/product/list";
    }
    @GetMapping("/admin/product/list")
    public String productList(Model model) {
       List<ProductDTO> products =productService.selectProducts();
        log.info(products.toString());
       model.addAttribute("products", products);

        return "/admin/product/list";
    }
}
