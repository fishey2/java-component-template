package com.roboautomator.component.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity(name = "Message")
@Table(name = "message")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class MessageEntity extends DefaultEntity {

    @Column(nullable = false)
    private String message;
}
