package com.storeflow.repository;

import com.storeflow.entity.Product;
import com.storeflow.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Product entity with custom query methods
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find a product by SKU (case-insensitive)
     * @param sku the product SKU
     * @return Optional containing the product if found
     */
    Optional<Product> findBySkuIgnoreCase(String sku);

    /**
     * Find all products in a specific category
     * @param category the category to search in
     * @return list of products in the category
     */
    List<Product> findByCategory(Category category);

    /**
     * Find all products with stock quantity below a threshold
     * @param threshold the stock quantity threshold
     * @return list of products below the threshold
     */
    List<Product> findAllByStockQuantityLessThan(Integer threshold);
}
