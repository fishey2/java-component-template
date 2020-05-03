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
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MessageEntity extends DefaultEntity {

    @Column(nullable = false)
    private String message;
}
