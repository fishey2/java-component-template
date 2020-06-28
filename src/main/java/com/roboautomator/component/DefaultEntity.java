package com.roboautomator.component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private UUID id;

    @EqualsAndHashCode.Exclude
    private UUID correlationId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @EqualsAndHashCode.Exclude
    private OffsetDateTime updatedAt;
}
