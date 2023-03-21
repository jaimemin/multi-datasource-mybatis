package com.tistory.jaimemin.multidatasourcemybatis.product.mapper;

import com.tistory.jaimemin.multidatasourcemybatis.product.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductDto> getAllProducts();

    void save(ProductDto newProduct);

    void delete(Integer id);
}
