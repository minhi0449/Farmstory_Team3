package com.farmstory.repository.impl;

import com.farmstory.entity.Product;

import com.farmstory.entity.QProduct;
import com.farmstory.repository.custom.ProductRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Autowired
    private final JPAQueryFactory queryFactory;
    private QProduct qProduct = QProduct.product;


    @Override
    public List<Product> selectProducts(){
        return queryFactory.select(qProduct)
                .from(qProduct).fetch();
    }

    @Override
    public Product selectProduct(int prodno) {
        return queryFactory
                .selectFrom(qProduct)
                .where(qProduct.prodNo.eq(prodno))
                .fetchOne();
    }

}
