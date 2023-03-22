package com.tistory.jaimemin.multidatasourcemybatis.tenant.service;

import com.tistory.jaimemin.multidatasourcemybatis.tenant.dto.ProductDto;
import com.tistory.jaimemin.multidatasourcemybatis.tenant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductDto> getAllProducts() {
        return repository.getAllProducts();
    }

    @Transactional
    public void save(ProductDto newProduct) {
        repository.save(newProduct);
    }

    @Transactional
    public void delete(Integer id) {
        repository.delete(id);
    }
}
