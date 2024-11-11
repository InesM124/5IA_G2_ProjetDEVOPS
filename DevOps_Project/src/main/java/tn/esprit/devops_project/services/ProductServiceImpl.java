package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IProductService;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    
    final ProductRepository productRepository;
    final StockRepository stockRepository;

    @Override
    public Product addProduct(Product product, Long idStock) {
        logger.info("Adding new product - Title: {}, Price: {}, Quantity: {}, Category: {}, Stock ID: {}", 
            product.getTitle(), 
            product.getPrice(), 
            product.getQuantity(), 
            product.getCategory(), 
            idStock);
        try {
            Stock stock = stockRepository.findById(idStock)
                .orElseThrow(() -> new NullPointerException("Stock not found with ID: " + idStock));
            product.setStock(stock);
            Product savedProduct = productRepository.save(product);
            logger.info("Successfully added product - ID: {}, Title: {}", 
                savedProduct.getIdProduct(), 
                savedProduct.getTitle());
            return savedProduct;
        } catch (Exception e) {
            logger.error("Failed to add product with title: {} - Error: {}", 
                product.getTitle(), 
                e.getMessage());
            throw e;
        }
    }

    @Override
    public Product retrieveProduct(Long id) {
        logger.info("Retrieving product with ID: {}", id);
        try {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Product not found with ID: " + id));
            logger.info("Retrieved product - ID: {}, Title: {}, Price: {}, Quantity: {}, Category: {}", 
                product.getIdProduct(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory());
            return product;
        } catch (Exception e) {
            logger.error("Failed to retrieve product with ID: {} - Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> retreiveAllProduct() {
        logger.info("Retrieving all products");
        try {
            List<Product> products = productRepository.findAll();
            logger.info("Retrieved {} products", products.size());
            products.forEach(product -> 
                logger.debug("Product - ID: {}, Title: {}, Price: {}, Quantity: {}, Category: {}", 
                    product.getIdProduct(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory())
            );
            return products;
        } catch (Exception e) {
            logger.error("Failed to retrieve all products - Error: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> retrieveProductByCategory(ProductCategory category) {
        logger.info("Retrieving products by category: {}", category);
        try {
            List<Product> products = productRepository.findByCategory(category);
            logger.info("Found {} products in category {}", products.size(), category);
            products.forEach(product -> 
                logger.debug("Product - ID: {}, Title: {}, Price: {}, Quantity: {}", 
                    product.getIdProduct(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getQuantity())
            );
            return products;
        } catch (Exception e) {
            logger.error("Failed to retrieve products for category: {} - Error: {}", 
                category, 
                e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            productRepository.deleteById(id);
            logger.info("Successfully deleted product with ID: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete product with ID: {} - Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> retreiveProductStock(Long id) {
        logger.info("Retrieving products for stock ID: {}", id);
        try {
            List<Product> products = productRepository.findByStockIdStock(id);
            logger.info("Found {} products in stock {}", products.size(), id);
            products.forEach(product -> 
                logger.debug("Product - ID: {}, Title: {}, Price: {}, Quantity: {}, Category: {}", 
                    product.getIdProduct(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategory())
            );
            return products;
        } catch (Exception e) {
            logger.error("Failed to retrieve products for stock ID: {} - Error: {}", 
                id, 
                e.getMessage());
            throw e;
        }
    }
}
