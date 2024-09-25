package com.apiproduct.service;

import com.apiproduct.service.usecase.CreateProductUsecase;
import com.apiproduct.service.usecase.GetProductListUseCase;
import com.domain.product.Product;
import com.infra.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<GetProductListUseCase> getProducts() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return products.stream().map(GetProductListUseCase::fromEntity).toList();
    }

    @Transactional
    public void saveProduct(CreateProductUsecase usecase) {
        productRepository.save(usecase.toEntity());
    }
}
