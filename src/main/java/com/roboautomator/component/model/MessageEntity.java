package com.roboautomator.component.model;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "Message")
@Table(name = "message")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String message;
}
