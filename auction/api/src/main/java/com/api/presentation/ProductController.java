package com.api.presentation;

import com.api.presentation.dto.CreateProductRequestDto;
import com.application.ProductService;
import com.application.usecase.CreateProductUsecase;
import com.application.usecase.GetProductListUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<GetProductListUseCase>> getProducts() {
        List<GetProductListUseCase> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> registerProduct(
            @Valid @RequestBody CreateProductRequestDto.Request request
            ) {
        CreateProductUsecase usecase = request.toUsecase();
        productService.saveProduct(usecase);
        return ResponseEntity.ok().build();

    }

}
