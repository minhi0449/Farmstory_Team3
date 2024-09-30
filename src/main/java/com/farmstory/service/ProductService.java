package com.farmstory.service;

import com.farmstory.dto.ProductDTO;
import com.farmstory.entity.Product;
import com.farmstory.entity.User;
import com.farmstory.repository.ProductRepository;
import com.farmstory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service

public class ProductService {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void insertProduct(ProductDTO productDTO, MultipartFile[] images) {


        String path = new File(uploadPath).getAbsolutePath();
        log.info(path);

        // images가 null이 아니고, 이미지가 있는 경우에만 처리
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];

                if (image != null && !image.isEmpty()) {
                    // 고유한 파일 이름 생성
                    String oName = image.getOriginalFilename();
                    String ext = oName.substring(oName.lastIndexOf("."));
                    String sName = UUID.randomUUID().toString() + ext;

                    // 파일 저장 경로 설정
                    File destFile = new File(path , sName);
                    log.info(destFile);

                    try {
                        log.info("ProductService > 파일 저장 before");
                        image.transferTo(destFile); // 파일을 저장
                        log.info("ProductService > 파일 저장 after");
                    } catch (IOException e) {
                        System.out.println("Fail Upload File"); // 예외 처리
                        log.info("ProductService > 파일 저장 error");
                        e.printStackTrace();
                    }

                    // 이미지 파일 이름 저장
                    if (i == 0) {
                        productDTO.setImg1(sName);
                    } else if (i == 1) {
                        productDTO.setImg2(sName);
                    } else if (i == 2) {
                        productDTO.setImg3(sName);
                    }
                }
            }
        } else {
            log.info("No images provided or images array is null.");
        }

        // ProductDTO를 Entity로 변환하여 저장
        Product entity = productDTO.toEntity();

        // uid에 해당하는 User 객체 찾아서 저장 전 추가
        String uid = productDTO.getUid();
        Optional<User> optUser = userRepository.findById(productDTO.getUid());
        User user = optUser.orElseThrow(() -> new RuntimeException("User Not Found"));
        entity.addUser(user);

        productRepository.save(entity);

        log.info("ProductService > insertProduct end");
    }

    public void updateProduct(ProductDTO productDTO, MultipartFile[] images) {
        String path = new File(uploadPath).getAbsolutePath();
        log.info(path);

        Product previousProduct = productRepository.findById(productDTO.getProdNo());
        log.info("수정 전 Product: "+previousProduct);
        // 이미지가 있는지 확인
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];

                if (image != null && !image.isEmpty()) {

                    String oName = image.getOriginalFilename();
                    String ext = oName.substring(oName.lastIndexOf("."));
                    String sName = UUID.randomUUID().toString() + ext;

                    File destFile = new File(path , sName);
                    log.info(destFile);

                    try {
                        image.transferTo(destFile); // 파일을 저장
                    } catch (IOException e) {
                        System.out.println("Fail Upload File"); // 예외 처리
                        e.printStackTrace();
                    }

                    // 이미지 파일 이름 저장
                    if (i == 0) {
                        productDTO.setImg1(sName);
                    } else if (i == 1) {
                        productDTO.setImg2(sName);
                    } else if (i == 2) {
                        productDTO.setImg3(sName);
                    }
                    // 사진을 변경하지 않았다면 여기로 넘어와서 기존의 값을 가지고 들어감
                }else {
                    if (i == 0) {
                        productDTO.setImg1(previousProduct.getImg1());
                    } else if (i == 1) {
                        productDTO.setImg2(previousProduct.getImg2());
                    } else if (i == 2) {
                        productDTO.setImg3(previousProduct.getImg3());
                    }
                }
            }
        } else {
            log.info("No images provided or images array is null.");
        }


        // ProductDTO를 Entity로 변환하여 저장
        Product entity = productDTO.toEntity();

        // uid에 해당하는 User 객체 찾아서 저장 전 추가
        String uid = productDTO.getUid();
        Optional<User> optUser = userRepository.findById(productDTO.getUid());
        User user = optUser.orElseThrow(() -> new RuntimeException("User Not Found"));
        entity.addUser(user);

        productRepository.save(entity);

        log.info("ProductService > updateProducts end");
    }


    public List<ProductDTO> selectProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(product.toDTO());
        }
        return productDTOs;
    }
    public ProductDTO findProductById(Integer prodNo) {
        Optional<Product> opt = productRepository.findById(prodNo);
        // Entity 존재 여부 확인
        if(opt.isPresent()){
            // Optional 타입으로 정의된 Entity 가져오기
            Product product = opt.get();

            // Entity를 DTO로 변환해서 반환
            return product.toDTO();
        }
        return null;
    }
    public void updateModifyById(Integer prodNo, ProductDTO productDTO) {
        Optional<Product> opt = productRepository.findById(prodNo);
        if(opt.isPresent()) {
            Product product = opt.get();
        }

    }
    public void deleteProductById(Integer prodNo) {
        productRepository.deleteById(prodNo);

    }
}


