package com.storeflow.repository;

import com.storeflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository for User entity with custom query methods
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by email address
     * @param email the user's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by reset token
     * @param resetToken the password reset token
     * @return Optional containing the user if found
     */
    Optional<User> findByResetToken(String resetToken);
}
