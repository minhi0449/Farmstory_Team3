package com.farmstory.repository.impl;

import com.farmstory.entity.Product;
import com.farmstory.entity.QProduct;
import com.farmstory.repository.custom.ProductRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProduct qProduct = QProduct.product;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Product.class);
        this.queryFactory = queryFactory;
    }


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
