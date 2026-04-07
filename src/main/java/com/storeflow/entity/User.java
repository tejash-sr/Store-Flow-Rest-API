package com.storeflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storeflow.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * User entity representing customer and admin accounts
 * Handles authentication, password reset, and role-based access
 */
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "avatar_path", length = 500)
    private String avatarPath;

    @Column(name = "reset_token", length = 500)
    private String resetToken;

    @Column(name = "reset_token_expires_at")
    private Instant resetTokenExpiresAt;

    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    /**
     * Check if the reset token is valid (non-null and not expired)
     * @return true if token exists and hasn't expired, false otherwise
     */
    @Transient
    public boolean isResetTokenValid() {
        return resetToken != null && resetTokenExpiresAt != null && resetTokenExpiresAt.isAfter(Instant.now());
    }
}
