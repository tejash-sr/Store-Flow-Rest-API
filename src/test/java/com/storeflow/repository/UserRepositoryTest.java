package com.storeflow.repository;

import com.storeflow.entity.User;
import com.storeflow.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRepository Tests")
class UserRepositoryTest extends AbstractDataJpaTest {

    @Autowired
    private UserRepository sut;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@example.com")
                .password("hashedPassword123")
                .fullName("Test User")
                .role(UserRole.USER)
                .enabled(true)
                .build();
    }

    @Test
    @DisplayName("Save user with valid data should succeed")
    void testSaveValidUser() {
        User saved = sut.save(testUser);
        
        assertNotNull(saved.getId());
        assertEquals("test@example.com", saved.getEmail());
        assertEquals("Test User", saved.getFullName());
        assertEquals(UserRole.USER, saved.getRole());
        assertTrue(saved.getEnabled());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    @DisplayName("User should have default role as USER")
    void testDefaultUserRole() {
        User user = User.builder()
                .email("user@test.com")
                .password("password123")
                .fullName("Test")
                .build();
        
        User saved = sut.save(user);
        assertEquals(UserRole.USER, saved.getRole());
    }

    @Test
    @DisplayName("Email uniqueness constraint should be enforced")
    void testEmailUniquenessConstraint() {
        sut.save(testUser);
        
        User duplicate = User.builder()
                .email("test@example.com")
                .password("anotherPassword")
                .fullName("Another User")
                .build();
        
        assertThrows(DataIntegrityViolationException.class, () -> sut.save(duplicate));
    }

    @Test
    @DisplayName("Find user by email should return the correct user")
    void testFindByEmail() {
        sut.save(testUser);
        
        var found = sut.findByEmail("test@example.com");
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getFullName());
    }

    @Test
    @DisplayName("Find user by email with non-existent email should return empty")
    void testFindByEmailNotFound() {
        var found = sut.findByEmail("nonexistent@example.com");
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("isResetTokenValid should return true for valid token")
    void testIsResetTokenValid() {
        testUser.setResetToken("validToken123");
        testUser.setResetTokenExpiresAt(Instant.now().plusSeconds(3600));
        sut.save(testUser);
        
        User found = sut.findByEmail("test@example.com").get();
        assertTrue(found.isResetTokenValid());
    }

    @Test
    @DisplayName("isResetTokenValid should return false for expired token")
    void testIsResetTokenExpired() {
        testUser.setResetToken("expiredToken");
        testUser.setResetTokenExpiresAt(Instant.now().minusSeconds(3600));
        sut.save(testUser);
        
        User found = sut.findByEmail("test@example.com").get();
        assertFalse(found.isResetTokenValid());
    }

    @Test
    @DisplayName("Find user by reset token should return the correct user")
    void testFindByResetToken() {
        testUser.setResetToken("uniqueToken123");
        testUser.setResetTokenExpiresAt(Instant.now().plusSeconds(3600));
        sut.save(testUser);
        
        var found = sut.findByResetToken("uniqueToken123");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }
}
