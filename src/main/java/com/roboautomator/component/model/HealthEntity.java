package com.roboautomator.component.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "Health")
@Table(name = "health")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class HealthEntity extends DefaultEntity{
    // Default Only
}