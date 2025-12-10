package org.ferrs.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.ferrs.dtos.ProductRequestDto;
import org.ferrs.dtos.ProductResponseDto;
import org.ferrs.entities.Product;
import org.ferrs.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ProductServiceTest {

  @InjectMock
  ProductRepository repository;

  @Inject
  ProductService service;

  @Test
  @DisplayName("Deve criar um produto corretamente")
  void shouldCreateProductSuccessfully() {
    // Arrange
    ProductRequestDto dto = ProductRequestDto.builder()
        .name("Monitor")
        .price(1500.0)
        .build();

    doAnswer(invocationOnMock -> {
      Product product = invocationOnMock.getArgument(0);
      product.setId(1L); // simula que o banco gerou ID
      return null;
    }).when(repository).persist(any(Product.class));

    // Act
    ProductResponseDto response = service.create(dto);

    // Assert
    verify(repository, times(1)).persist(any(Product.class));
  }

  @Test
  @DisplayName("Deve criar o valor do produto com a taxa corretamente")
  void shouldCreateProductWithTaxSuccessfully() {
    // Arrange
    ProductRequestDto dto = new ProductRequestDto("Monitor", 1500.0, 110.0);

    doAnswer(invocationOnMock -> {
      Product product = invocationOnMock.getArgument(0);
      product.setId(1L); // simula que o banco gerou ID
      return null;
    }).when(repository).persist(any(Product.class));

    // Act
    ProductResponseDto response = service.create(dto);

    // Assert
    verify(repository, times(1)).persist(any(Product.class));
  }

  @Test
  void shouldThrowWhenPriceIsNegative() {
    // Arrange
    ProductRequestDto dto = new ProductRequestDto("Monitor", -10.0, null);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> service.create(dto));
  }

}

