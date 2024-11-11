package tn.esprit.devops_project.services;

import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testAddProduct() {
        // Arrange
        Product product = new Product(null, "Test Product", 9.99f, 10, ProductCategory.ELECTRONICS, null);
        Stock stock = new Stock(1L, "Test Stock", new HashSet<>());
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product savedProduct = productService.addProduct(product, 1L);

        // Assert
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals("Test Product", savedProduct.getTitle());
        Assertions.assertEquals(9.99f, savedProduct.getPrice());
        Assertions.assertEquals(10, savedProduct.getQuantity());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, savedProduct.getCategory());
        Assertions.assertEquals(stock, savedProduct.getStock());
        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    // Rest of the test methods remain the same


    @Test
    void testRetrieveProduct() {
        // Arrange
        Product product = new Product(1L, "Test Product", 9.99f, 10, ProductCategory.ELECTRONICS, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product retrievedProduct = productService.retrieveProduct(1L);

        // Assert
        Assertions.assertNotNull(retrievedProduct);
        Assertions.assertEquals(1L, retrievedProduct.getIdProduct());
        Assertions.assertEquals("Test Product", retrievedProduct.getTitle());
        Assertions.assertEquals(9.99f, retrievedProduct.getPrice());
        Assertions.assertEquals(10, retrievedProduct.getQuantity());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, retrievedProduct.getCategory());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllProducts() {
       // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", 9.99f, 10, ProductCategory.ELECTRONICS, null));
        products.add(new Product(2L, "Product 2", 19.99f, 5, ProductCategory.CLOTHING, null));
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> retrievedProducts = productService.retreiveAllProduct();

        // Assert
        Assertions.assertNotNull(retrievedProducts);
        Assertions.assertEquals(2, retrievedProducts.size());
        Assertions.assertEquals("Product 1", retrievedProducts.get(0).getTitle());
        Assertions.assertEquals("Product 2", retrievedProducts.get(1).getTitle());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveProductsByCategory() {
        // Arrange
        List<Product> electronicsProducts = new ArrayList<>();
        electronicsProducts.add(new Product(1L, "Product 1", 9.99f, 10, ProductCategory.ELECTRONICS, null));
        electronicsProducts.add(new Product(2L, "Product 2", 19.99f, 5, ProductCategory.ELECTRONICS, null));
        when(productRepository.findByCategory(ProductCategory.ELECTRONICS)).thenReturn(electronicsProducts);

        // Act
        List<Product> retrievedProducts = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);

        // Assert
        Assertions.assertNotNull(retrievedProducts);
        Assertions.assertEquals(2, retrievedProducts.size());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, retrievedProducts.get(0).getCategory());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, retrievedProducts.get(1).getCategory());
        verify(productRepository, times(1)).findByCategory(ProductCategory.ELECTRONICS);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        long productId = 1L;

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testRetrieveProductStock() {
        // Arrange
        List<Product> stockProducts = new ArrayList<>();
        stockProducts.add(new Product(1L, "Product 1", 9.99f, 10, ProductCategory.ELECTRONICS, null));
        stockProducts.add(new Product(2L, "Product 2", 19.99f, 5, ProductCategory.CLOTHING, null));
        when(productRepository.findByStockIdStock(1L)).thenReturn(stockProducts);

        // Act
        List<Product> retrievedProducts = productService.retreiveProductStock(1L);

        // Assert
        Assertions.assertNotNull(retrievedProducts);
        Assertions.assertEquals(2, retrievedProducts.size());
        Assertions.assertEquals(1L, retrievedProducts.get(0).getIdProduct());
        Assertions.assertEquals(2L, retrievedProducts.get(1).getIdProduct());
        verify(productRepository, times(1)).findByStockIdStock(1L);
    }
}
