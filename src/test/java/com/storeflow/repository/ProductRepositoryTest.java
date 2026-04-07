package com.storeflow.repository;

import com.storeflow.AbstractRepositoryTest;
import com.storeflow.entity.Category;
import com.storeflow.entity.Product;
import com.storeflow.enums.CategoryStatus;
import com.storeflow.enums.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductRepository Tests")
class ProductRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    private Category testCategory;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .name("Electronics")
                .status(CategoryStatus.ACTIVE)
                .build();
        testCategory = categoryRepo.save(testCategory);

        testProduct = Product.builder()
                .name("Laptop")
                .description("High-performance laptop")
                .sku("LAP-001")
                .price(new BigDecimal("999.99"))
                .stockQuantity(10)
                .category(testCategory)
                .status(ProductStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Save product with valid data should succeed")
    void testSaveValidProduct() {
        Product saved = productRepo.save(testProduct);
        
        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getName());
        assertEquals("LAP-001", saved.getSku());
        assertEquals(new BigDecimal("999.99"), saved.getPrice());
        assertEquals(10, saved.getStockQuantity());
    }

    @Test
    @DisplayName("SKU uniqueness constraint should be enforced")
    void testSkuUniquenessConstraint() {
        productRepo.save(testProduct);
        
        Product duplicate = Product.builder()
                .name("Another Laptop")
                .description("Different laptop")
                .sku("LAP-001")
                .price(new BigDecimal("799.99"))
                .stockQuantity(5)
                .category(testCategory)
                .build();
        
        assertThrows(DataIntegrityViolationException.class, () -> productRepo.save(duplicate));
    }

    @Test
    @DisplayName("Find product by SKU ignore case should work")
    void testFindBySkuIgnoreCase() {
        productRepo.save(testProduct);
        
        var found = productRepo.findBySkuIgnoreCase("lap-001");
        assertTrue(found.isPresent());
        assertEquals("Laptop", found.get().getName());
    }

    @Test
    @DisplayName("Find products by category should return all products in category")
    void testFindByCategory() {
        productRepo.save(testProduct);
        
        Product product2 = Product.builder()
                .name("Tablet")
                .description("Portable tablet")
                .sku("TAB-001")
                .price(new BigDecimal("399.99"))
                .stockQuantity(20)
                .category(testCategory)
                .build();
        productRepo.save(product2);
        
        List<Product> products = productRepo.findByCategory(testCategory);
        assertEquals(2, products.size());
    }

    @Test
    @DisplayName("Find products with stock below threshold should work")
    void testFindAllByStockQuantityLessThan() {
        productRepo.save(testProduct); // 10 units
        
        Product lowStockProduct = Product.builder()
                .name("Mouse")
                .description("Wireless mouse")
                .sku("MOU-001")
                .price(new BigDecimal("29.99"))
                .stockQuantity(3)
                .category(testCategory)
                .build();
        productRepo.save(lowStockProduct);
        
        List<Product> lowStockItems = productRepo.findAllByStockQuantityLessThan(5);
        assertEquals(1, lowStockItems.size());
        assertEquals("Mouse", lowStockItems.get(0).getName());
    }

    @Test
    @DisplayName("Default product status should be ACTIVE")
    void testDefaultProductStatus() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test")
                .sku("TST-001")
                .price(new BigDecimal("10.00"))
                .stockQuantity(5)
                .category(testCategory)
                .build();
        
        Product saved = productRepo.save(product);
        assertEquals(ProductStatus.ACTIVE, saved.getStatus());
    }
}
