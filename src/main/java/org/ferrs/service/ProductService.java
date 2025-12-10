package org.ferrs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ferrs.dtos.ProductRequestDto;
import org.ferrs.dtos.ProductResponseDto;
import org.ferrs.entities.Product;
import org.ferrs.repository.ProductRepository;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository repository;

    @Transactional
    public ProductResponseDto create(ProductRequestDto dto) {
        validatePrice(dto.getPrice());
        double finalPrice = calculateFinalPrice(dto.getPrice(), dto.getTax());
        Product product = Product.builder()
                .name(dto.getName())
                .price(finalPrice) 
                .build();
        repository.persist(product);
        return toResponseDto(product);
    }

    public List<ProductResponseDto> listAll() {
        return repository.listAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public ProductResponseDto findById(Long id) {
        Product product = findProductOrThrow(id);
        return toResponseDto(product);
    }

    @Transactional
    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        validatePrice(dto.getPrice());
        Product product = findProductOrThrow(id);
        product.setName(dto.getName());
        product.setPrice(calculateFinalPrice(dto.getPrice(), dto.getTax()));
        return toResponseDto(product);
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new IllegalArgumentException("Product not found");
        }
    }

    private void validatePrice(Double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    private double calculateFinalPrice(Double price, Double tax) {
        return price + (tax != null ? tax : 0);
    }

    private Product findProductOrThrow(Long id) {
        Product product = repository.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return product;
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice());
    }
}
