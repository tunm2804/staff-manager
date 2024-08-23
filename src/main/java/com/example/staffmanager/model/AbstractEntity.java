package com.example.staffmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "status")
    private Byte status = 1;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "last_modified_date")
    private Long lastModifiedDate;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (status == null) {
            status = 1;
        }
        if (createdDate == null) {
            createdDate = System.currentTimeMillis();
        }
        if (lastModifiedDate == null) {
            this.lastModifiedDate = System.currentTimeMillis();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = System.currentTimeMillis();
    }
}
