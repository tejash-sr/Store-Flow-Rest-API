package com.storeflow.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;

/**
 * Audit listener for managing createdAt and updatedAt timestamps
 * Automatically sets timestamps on entity creation and updates
 */
public class AuditListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedAt(Instant.now());
    }
}
