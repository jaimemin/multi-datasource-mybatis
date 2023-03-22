package com.tistory.jaimemin.multidatasourcemybatis.tenant.controller;

import com.tistory.jaimemin.multidatasourcemybatis.tenant.dto.ProductDto;
import com.tistory.jaimemin.multidatasourcemybatis.tenant.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<?> save(HttpServletRequest request,  @RequestBody ProductDto newProduct) {
        try {
            newProduct.setCreatedBy(request.getHeader("x-tenant-id"));
            productService.save(newProduct);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            log.error("[ProductController.save] ERROR ", e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Integer id) {
        try {
            productService.delete(id);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            log.error("[ProductController.delete] ERROR ", e.getMessage());

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
