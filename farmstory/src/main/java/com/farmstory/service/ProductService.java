package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Product;
import com.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    public static String uploadDir = System.getProperty("user.dir")+"/uploads/";


    public void insertProduct(ProductDTO productDTO, MultipartFile image) {
        log.info(uploadDir);
        log.info(image.toString());

        if (image != null && !image.isEmpty()) {
            // 고유한 파일 이름 생성
            String oName = image.getOriginalFilename();
            String sName = UUID.randomUUID().toString() + "_" + oName;

            // 업로드 경로가 존재하지 않으면 생성
            log.info(uploadDir);
            // 파일 저장
            File destFile = new File(uploadDir + sName);
            try {
                image.transferTo(destFile); // 파일을 저장
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
                // 예외 발생 시 로깅 또는 추가 처리 필요
            }

            // 이미지 파일 이름 저장
            productDTO.setImg1(sName);
        }

        Product entity = productDTO.toEntity();
        productRepository.save(entity);
    }
    public List<ProductDTO> selectProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(product.toDTO());
        }
        return productDTOs;
    }
}

