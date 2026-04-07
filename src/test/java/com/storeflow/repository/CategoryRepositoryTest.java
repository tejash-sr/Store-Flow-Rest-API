package com.storeflow.repository;

import com.storeflow.entity.Category;
import com.storeflow.enums.CategoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryRepository Tests")
class CategoryRepositoryTest extends AbstractDataJpaTest {

    @Autowired
    private CategoryRepository sut;

    private Category parentCategory;
    private Category childCategory;

    @BeforeEach
    void setUp() {
        parentCategory = Category.builder()
                .name("Electronics")
                .description("Electronic devices")
                .status(CategoryStatus.ACTIVE)
                .build();

        childCategory = Category.builder()
                .name("Laptops")
                .description("Laptop computers")
                .status(CategoryStatus.ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Save category with valid data should succeed")
    void testSaveValidCategory() {
        Category saved = sut.save(parentCategory);
        
        assertNotNull(saved.getId());
        assertEquals("Electronics", saved.getName());
        assertNull(saved.getParent());
        assertEquals(CategoryStatus.ACTIVE, saved.getStatus());
    }

    @Test
    @DisplayName("Self-referencing parent-child relationship should work")
    void testParentChildRelationship() {
        Category savedParent = sut.save(parentCategory);
        
        childCategory.setParent(savedParent);
        Category savedChild = sut.save(childCategory);
        
        assertNotNull(savedChild.getParent());
        assertEquals(savedParent.getId(), savedChild.getParent().getId());
        assertEquals("Electronics", savedChild.getParent().getName());
    }

    @Test
    @DisplayName("Default status should be ACTIVE")
    void testDefaultStatus() {
        Category category = Category.builder()
                .name("Test Category")
                .build();
        
        Category saved = sut.save(category);
        assertEquals(CategoryStatus.ACTIVE, saved.getStatus());
    }

    @Test
    @DisplayName("Category should have audit timestamps")
    void testAuditTimestamps() {
        Category saved = sut.save(parentCategory);
        
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }
}
