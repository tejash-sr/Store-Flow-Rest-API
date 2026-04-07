package com.storeflow.dto;

import com.storeflow.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * DTO for Category entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private CategoryStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
