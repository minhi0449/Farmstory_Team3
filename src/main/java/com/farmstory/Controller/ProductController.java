package com.farmstory.Controller;

import com.farmstory.dto.ProductDTO;

import com.farmstory.entity.Product;
import com.farmstory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

// 수빈님 - product
@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    // 관리자 - 상품 등록
    @GetMapping("/admin/product/register")
    public String productRegister() {
        return "/admin/product/register";
    }
    @PostMapping("/admin/product/register")
    public String productRegister(ProductDTO productDTO, MultipartFile[] images) {

        productService.insertProduct(productDTO,images);
        return "redirect:/admin/product/list";
    }

    // 관리자 - 상품 목록
    @GetMapping("/admin/product/list")
    public String productList(Model model) {

       List<ProductDTO> products =productService.selectProducts();
        log.info(products.toString());
       model.addAttribute("products", products);

        return "/admin/product/list";
    }

    // 상품 보기 (상세 페이지)
    @GetMapping("/market/view")
    public String view(@RequestParam("prodNo") int prodNo, Model model) {
        ProductDTO product = productService.findProductById(prodNo);

        model.addAttribute("product", product);

        return "/market/view";
    }

    // 상품 목록
    @GetMapping("/market/list")
    public String list(Model model) {

        List<ProductDTO> products = productService.selectProducts();
        log.info(products.toString());
        model.addAttribute("products", products);

        return "/market/list";
    }

    @PostMapping("/market/delete")
    public String delete(@RequestParam("prodNo") List<String> products) {

        for (String prodNo  : products) {
            productService.deleteProductById(Integer.parseInt(prodNo));
        }
        return "redirect:/admin/product/list";  // 삭제 후 상품 목록으로 리다이렉트
    }

    // 상품 수정
    @GetMapping("/admin/product/modify")
    public String modifyselect(@RequestParam("prodNo") int prodNo, Model model) {

        ProductDTO product = productService.findProductById(prodNo);

        model.addAttribute("product", product);

        return "/admin/product/modify";
    }
    @PostMapping("/admin/product/modify")
    public String modify(@RequestParam("prodNo") int prodNo, ProductDTO productDTO, MultipartFile[] images) {

        productService.updateProduct(productDTO, images);

        return "redirect:/admin/product/list";
    }
}


