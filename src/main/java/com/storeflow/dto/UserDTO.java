package com.storeflow.dto;

import com.storeflow.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * DTO for User entity
 * Used for request/response serialization (password excluded)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private String avatarPath;
    private Boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
