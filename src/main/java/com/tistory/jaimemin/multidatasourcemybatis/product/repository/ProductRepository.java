package com.tistory.jaimemin.multidatasourcemybatis.product.repository;

import com.tistory.jaimemin.multidatasourcemybatis.product.dto.ProductDto;
import com.tistory.jaimemin.multidatasourcemybatis.product.mapper.ProductMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final ProductMapper mapper;

    public ProductRepository(@Qualifier("tenantSqlSession") SqlSession sqlSession) {
        mapper = sqlSession.getMapper(ProductMapper.class);
    }

    public List<ProductDto> getAllProducts() {
        return mapper.getAllProducts();
    }

    public void save(ProductDto newProduct) {
        mapper.save(newProduct);
    }

    public void delete(Integer id) {
        mapper.delete(id);
    }
}
