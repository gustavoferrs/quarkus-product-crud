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

import java.util.List;

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
    assertEquals("Monitor", response.name());
    assertEquals(1500.0, response.price());
    assertEquals(1L, response.id());
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
    assertEquals("Monitor", response.name());
    assertEquals(1610.0, response.price()); // O valor esperado foi alterado para refletir o cálculo com a taxa
    assertEquals(1L, response.id());
    verify(repository, times(1)).persist(any(Product.class));
  }

  @Test
  void shouldThrowWhenPriceIsNegative() {
    // Arrange
    ProductRequestDto dto = new ProductRequestDto("Monitor", -10.0, null);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> service.create(dto));
  }

  @Test
  @DisplayName("Deve listar todos os produtos corretamente")
  void shouldListAllProductsSuccessfully() {
    // Arrange
    Product product1 = Product.builder().id(1L).name("Monitor").price(1500.0).build();
    Product product2 = Product.builder().id(2L).name("Teclado").price(200.0).build();
    when(repository.listAll()).thenReturn(List.of(product1, product2));

    // Act
    var result = service.listAll();

    // Assert
    assertEquals(2, result.size());
    assertEquals("Monitor", result.get(0).name());
    assertEquals("Teclado", result.get(1).name());
  }

  @Test
  @DisplayName("Deve retornar lista vazia quando não houver produtos")
  void shouldReturnEmptyListWhenNoProducts() {
    when(repository.listAll()).thenReturn(List.of());
    var result = service.listAll();
    assertTrue(result.isEmpty());
  }

  @Test
  @DisplayName("Deve encontrar produto por ID com sucesso")
  void shouldFindProductByIdSuccessfully() {
    Product product = Product.builder().id(1L).name("Monitor").price(1500.0).build();
    when(repository.findById(1L)).thenReturn(product);
    var result = service.findById(1L);
    assertEquals(1L, result.id());
    assertEquals("Monitor", result.name());
  }

  @Test
  @DisplayName("Deve lançar exceção ao buscar produto inexistente por ID")
  void shouldThrowWhenProductNotFoundById() {
    when(repository.findById(99L)).thenReturn(null);
    assertThrows(IllegalArgumentException.class, () -> service.findById(99L));
  }

  @Test
  @DisplayName("Deve atualizar produto com sucesso")
  void shouldUpdateProductSuccessfully() {
    Product existing = Product.builder().id(1L).name("Monitor").price(1500.0).build();
    when(repository.findById(1L)).thenReturn(existing);
    ProductRequestDto dto = new ProductRequestDto("Monitor 4K", 2000.0, 100.0);
    var result = service.update(1L, dto);
    assertEquals("Monitor 4K", existing.getName());
    assertEquals(2100.0, existing.getPrice());
    assertEquals("Monitor 4K", result.name());
    assertEquals(2100.0, result.price());
  }

  @Test
  @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
  void shouldThrowWhenUpdateProductNotFound() {
    when(repository.findById(99L)).thenReturn(null);
    ProductRequestDto dto = new ProductRequestDto("Monitor", 100.0, 10.0);
    assertThrows(IllegalArgumentException.class, () -> service.update(99L, dto));
  }

  @Test
  @DisplayName("Deve deletar produto com sucesso")
  void shouldDeleteProductSuccessfully() {
    when(repository.deleteById(1L)).thenReturn(true);
    assertDoesNotThrow(() -> service.delete(1L));
    verify(repository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Deve lançar exceção ao deletar produto inexistente")
  void shouldThrowWhenDeleteProductNotFound() {
    when(repository.deleteById(99L)).thenReturn(false);
    assertThrows(IllegalArgumentException.class, () -> service.delete(99L));
  }

}
