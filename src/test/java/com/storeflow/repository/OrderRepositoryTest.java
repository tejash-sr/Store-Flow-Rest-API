package com.storeflow.repository;

import com.storeflow.AbstractRepositoryTest;
import com.storeflow.entity.*;
import com.storeflow.enums.CategoryStatus;
import com.storeflow.enums.OrderStatus;
import com.storeflow.enums.ProductStatus;
import com.storeflow.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderRepository and OrderItem Tests")
class OrderRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    private User testCustomer;
    private Product testProduct;
    private Order testOrder;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        testCustomer = User.builder()
                .email("customer@test.com")
                .password("password123")
                .fullName("Test Customer")
                .role(UserRole.USER)
                .build();
        testCustomer = userRepo.save(testCustomer);

        Category category = Category.builder()
                .name("Test Category")
                .status(CategoryStatus.ACTIVE)
                .build();
        category = categoryRepo.save(category);

        testProduct = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .sku("TEST-001")
                .price(new BigDecimal("100.00"))
                .stockQuantity(10)
                .category(category)
                .status(ProductStatus.ACTIVE)
                .build();
        testProduct = productRepo.save(testProduct);

        testOrder = Order.builder()
                .referenceNumber("ORD-001")
                .customer(testCustomer)
                .orderItems(new ArrayList<>())
                .status(OrderStatus.PENDING)
                .shippingAddress(ShippingAddress.builder()
                        .street("123 Main St")
                        .city("Test City")
                        .country("Test Country")
                        .postalCode("12345")
                        .build())
                .totalAmount(new BigDecimal("100.00"))
                .build();

        testOrderItem = OrderItem.builder()
                .product(testProduct)
                .quantity(1)
                .unitPrice(new BigDecimal("100.00"))
                .subtotal(new BigDecimal("100.00"))
                .build();
    }

    @Test
    @DisplayName("Save order with valid data should succeed")
    void testSaveValidOrder() {
        Order saved = orderRepo.save(testOrder);
        
        assertNotNull(saved.getId());
        assertEquals("ORD-001", saved.getReferenceNumber());
        assertEquals(OrderStatus.PENDING, saved.getStatus());
        assertNotNull(saved.getShippingAddress());
    }

    @Test
    @DisplayName("Order cascade should persist order items")
    void testOrderCascadeOperations() {
        testOrderItem.setOrder(testOrder);
        testOrder.getOrderItems().add(testOrderItem);
        
        Order saved = orderRepo.save(testOrder);
        assertNotNull(saved.getId());
        assertEquals(1, saved.getOrderItems().size());
        
        Order retrieved = orderRepo.findById(saved.getId()).get();
        assertEquals(1, retrieved.getOrderItems().size());
        assertEquals("Test Product", retrieved.getOrderItems().get(0).getProduct().getName());
    }

    @Test
    @DisplayName("Total amount should be consistent with order items")
    void testTotalAmountConsistency() {
        BigDecimal expectedTotal = new BigDecimal("300.00");
        
        testOrder.setTotalAmount(expectedTotal);
        testOrderItem.setOrder(testOrder);
        testOrder.getOrderItems().add(testOrderItem);
        
        Order saved = orderRepo.save(testOrder);
        
        Order retrieved = orderRepo.findById(saved.getId()).get();
        assertEquals(expectedTotal, retrieved.getTotalAmount());
    }

    @Test
    @DisplayName("Order default status should be PENDING")
    void testDefaultOrderStatus() {
        Order order = Order.builder()
                .referenceNumber("ORD-002")
                .customer(testCustomer)
                .orderItems(new ArrayList<>())
                .shippingAddress(ShippingAddress.builder()
                        .street("456 Oak Ave")
                        .city("Another City")
                        .country("Another Country")
                        .postalCode("54321")
                        .build())
                .totalAmount(new BigDecimal("50.00"))
                .build();
        
        Order saved = orderRepo.save(order);
        assertEquals(OrderStatus.PENDING, saved.getStatus());
    }

    @Test
    @DisplayName("Save order item with valid data should succeed")
    void testSaveValidOrderItem() {
        Order savedOrder = orderRepo.save(testOrder);
        testOrderItem.setOrder(savedOrder);
        
        OrderItem saved = orderItemRepo.save(testOrderItem);
        
        assertNotNull(saved.getId());
        assertEquals(1, saved.getQuantity());
        assertEquals(new BigDecimal("100.00"), saved.getUnitPrice());
    }
}
